package com.sunset.rider.msvclabroom.config;

import com.sunset.rider.msvclabroom.handler.RoomHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> rutasRoom(RoomHandler roomHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/room"), roomHandler::findAll)
                .andRoute(RequestPredicates.GET("/room/{id}"), roomHandler::findById)
                .andRoute(RequestPredicates.POST("/room"), roomHandler::save)
                .andRoute(RequestPredicates.PUT("/room/{id}"), roomHandler::update)
                .andRoute(RequestPredicates.DELETE("/room/{id}"), roomHandler::delete)
                .andRoute(RequestPredicates.GET("/type"),roomHandler::getAllTypes);
    }

}
