package com.sunset.rider.msvclabroom.config;

import com.sunset.rider.msvclabroom.handler.RoomHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
    @Value("${apis.find-all}")
    private String findAll;

    @Value("${apis.get-by-room-id}")
    private String findById;
    @Value("${apis.create-room}")
    private String save;
    @Value("${apis.updated-by-room-id}")
    private String update;
    @Value("${apis.deleted-by-room-id}")
    private String delete;
    @Value("${apis.find-all-types}")
    private String findAllType;

    @Value("${apis.find-all-room-by-hotel}")
    private String findByHotelId;

    @Bean
    public RouterFunction<ServerResponse> rutasRoom(RoomHandler roomHandler) {
        return RouterFunctions.route(RequestPredicates.GET(findAll), roomHandler::findAll)
                .andRoute(RequestPredicates.GET(findById), roomHandler::findById)
                .andRoute(RequestPredicates.POST(save), roomHandler::save)
                .andRoute(RequestPredicates.PUT(update), roomHandler::update)
                .andRoute(RequestPredicates.DELETE(delete), roomHandler::delete)
                .andRoute(RequestPredicates.GET(findAllType), roomHandler::getAllTypes)
                .andRoute(RequestPredicates.GET(findByHotelId),roomHandler::finByHotelId);
    }

}
