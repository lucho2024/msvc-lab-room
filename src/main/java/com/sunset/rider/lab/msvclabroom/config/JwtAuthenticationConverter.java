package com.sunset.rider.lab.msvclabroom.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();


    private static final String RESOURCE_ACCESS = "resource_access";
    private static final String ROLES = "roles";



    @Value("${jwt.attribute.preferredUsername:preferred_username}")
    private String preferredUsername;

    @Value("${jwt.attribute.clientId:web-client-api}")
    private String clientId;

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {

        Collection<GrantedAuthority> authorities = Stream.concat(converter.convert(jwt).stream(), extractResourceRoles(jwt).stream()).toList();


        return Mono.just(new JwtAuthenticationToken(jwt, authorities, getPrincipalUserName(jwt)));

    }

    private String getPrincipalUserName(Jwt jwt){
        String claimName = JwtClaimNames.SUB;

        if(preferredUsername != null){
            claimName = preferredUsername;
        }
            return jwt.getClaim(preferredUsername);
    }



    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt){
        Map<String, Object> resourceAccess ;
        Map<String, Object>  client;


        if(jwt.getClaimAsMap(RESOURCE_ACCESS) == null){
            return Set.of();
        }

        resourceAccess =  jwt.getClaimAsMap(RESOURCE_ACCESS);

        if(resourceAccess.get(clientId)== null){
            return Set.of();
        }
        client = (Map) resourceAccess.get(clientId);

        log.info("resource_access: " + client);

        Collection<String> roles =  ( Collection<String>)  client.get(ROLES);

        log.info("JWT: " + resourceAccess.toString());
        log.info("authorities: " + roles.toString());
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_".concat(role).toUpperCase(Locale.ROOT))).toList();

    }
}
