package com.sunset.rider.lab.msvclabroom.router;

import com.sunset.rider.lab.msvclabroom.handler.RoomHandler;
import com.sunset.rider.lab.msvclabroom.models.document.Room;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RouterOperations({
            @RouterOperation(method = RequestMethod.GET, path = "/room",
                    operation = @Operation(description = "Find All",
                            operationId = "find-all", tags = "room",
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Room.class))))),
            @RouterOperation(method = RequestMethod.GET,path = "/room/{roomId}",
                    operation = @Operation(description = "Find By Id",
                            operationId = "find-by-id", tags = "room",
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Room.class))))),
            @RouterOperation(method = RequestMethod.POST,path = "/room",
                    operation = @Operation(description = "Create room",
                            operationId = "create-room", tags = "room",
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Room.class))))),
            @RouterOperation(method = RequestMethod.PUT,path = "/room/{roomId}",
                    operation = @Operation(description = "Updated room",
                            operationId = "updated-by-room-id", tags = "room",
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Room.class))))),
            @RouterOperation(method = RequestMethod.DELETE,path = "/room/{roomId}",
                    operation = @Operation(description = "Delete room",
                            operationId = "deleted-by-room-id", tags = "room",
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Room.class))))),
            @RouterOperation(method = RequestMethod.GET,path = "/type",
                    operation = @Operation(description = "find all Types",
                            operationId = "find-all-types", tags = "roomType",
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Room.class))))),
            @RouterOperation(method = RequestMethod.GET,path = "/room/hotel/{roomId}",
                    operation = @Operation(description = "find by hotel id",
                            operationId = "find-by-hotel-id", tags = "room",
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Room.class))))),

    })
    public RouterFunction<ServerResponse> rutasRoom(RoomHandler roomHandler) {
        return RouterFunctions.route(RequestPredicates.GET(findAll), roomHandler::findAll)
                .andRoute(RequestPredicates.GET(findById), roomHandler::findById)
                .andRoute(RequestPredicates.POST(save), roomHandler::save)
                .andRoute(RequestPredicates.PUT(update), roomHandler::update)
                .andRoute(RequestPredicates.DELETE(delete), roomHandler::delete)
                .andRoute(RequestPredicates.GET(findAllType), roomHandler::getAllTypes)
                .andRoute(RequestPredicates.GET(findByHotelId), roomHandler::finByHotelId);
    }

}
