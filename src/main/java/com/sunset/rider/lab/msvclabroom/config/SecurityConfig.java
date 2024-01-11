package com.sunset.rider.lab.msvclabroom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Autowired
    private JwtAuthenticationConverter jwtAuthenticationConverter;
    private static final String[] excludedAuthPages = {
            "/test/excludedAuthPages",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"
    };

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) throws Exception {


        return httpSecurity.authorizeExchange(exchanges -> exchanges
                        .pathMatchers(excludedAuthPages).permitAll()
                        .pathMatchers(HttpMethod.GET,"/room").permitAll()
                        .pathMatchers(HttpMethod.GET,"/room/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/type").permitAll()
                        .pathMatchers("/room").hasAuthority("ROLE_ADMIN")
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth -> {
                    oauth.jwt(jwt -> {
                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverter);
                    });
                })
                .csrf(csrf -> csrf.disable())
                .build();

    }


}
