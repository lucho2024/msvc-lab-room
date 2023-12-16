package com.sunset.rider.msvclabroom.handler;

import com.sunset.rider.msvclabroom.models.document.Room;
import com.sunset.rider.msvclabroom.models.document.RoomType;
import com.sunset.rider.msvclabroom.models.request.RoomRequest;
import com.sunset.rider.msvclabroom.services.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

@Component
public class RoomHandler {

    @Autowired
    private RoomServiceImpl roomService;


    public Mono<ServerResponse> findAll(ServerRequest request) {

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(roomService.findAll(), Room.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {

        String id = request.pathVariable("id");

        return roomService.findById(id).flatMap(room -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(room)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<RoomRequest> roomRequest = request.bodyToMono(RoomRequest.class);

        return roomRequest.flatMap(rq -> roomService.save(buildDbObjet(rq))
                .flatMap(room -> ServerResponse.created(URI.create("/room/".concat(room.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(room))
                )
        ).onErrorResume(error -> {
            WebClientResponseException errorResponse =
                    (WebClientResponseException) error;

            if (errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return ServerResponse.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(errorResponse.getResponseBodyAsString()));
            }
            return Mono.error(errorResponse);
        });
    }

    public Mono<ServerResponse> update(ServerRequest request) {

        String id = request.pathVariable("id");
        Mono<RoomRequest> roomRequestMono = request.bodyToMono(RoomRequest.class);

        return roomService.findById(id)
                .flatMap(room ->
                        roomRequestMono.flatMap(rq -> roomService.updated(buildDbObjetUp(rq, room))))
                .flatMap(room -> ServerResponse.created(URI.create("/room/".concat(room.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(room))
                ).onErrorResume(error -> {
                    WebClientResponseException errorResponse =
                            (WebClientResponseException) error;

                    if (errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(errorResponse.getResponseBodyAsString()));
                    }
                    return Mono.error(errorResponse);
                });


    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return roomService.delete(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getAllTypes(ServerRequest request) {


        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(roomService.findAllRoomTypes(), RoomType.class);

    }

    public Room buildDbObjet(RoomRequest roomRequest) {

        return Room.builder().roomNumber(roomRequest.getRoomNumber())
                .floor(roomRequest.getFloor())
                .HotelId(roomRequest.getHotelId())
                .maxGuest(roomRequest.getMaxGuest())
                .description(roomRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .roomType(
                        RoomType.builder().id(roomRequest.getRoomTypeRequest().getId())
                                .name(roomRequest.getRoomTypeRequest().getName())
                                .build())
                .build();

    }

    public Room buildDbObjetUp(RoomRequest roomRequest, Room room) {

        return Room.builder()
                .id(room.getId())
                .roomNumber(roomRequest.getRoomNumber())
                .floor(roomRequest.getFloor())
                .HotelId(roomRequest.getHotelId())
                .maxGuest(roomRequest.getMaxGuest())
                .description(roomRequest.getDescription())
                .createdAt(room.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .roomType(
                        RoomType.builder().id(roomRequest.getRoomTypeRequest().getId())
                                .name(roomRequest.getRoomTypeRequest().getName())
                                .build())
                .build();

    }


}
