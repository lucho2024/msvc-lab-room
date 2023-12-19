package com.sunset.rider.msvclabroom.handler;

import com.sunset.rider.msvclabroom.models.document.Room;
import com.sunset.rider.msvclabroom.models.document.RoomType;
import com.sunset.rider.msvclabroom.models.request.RoomRequest;
import com.sunset.rider.msvclabroom.models.utils.ErrorNotFound;
import com.sunset.rider.msvclabroom.services.RoomServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoomHandler {

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private Validator validator;


    public Mono<ServerResponse> findAll(ServerRequest request) {

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(roomService.findAll(), Room.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {

        String id = request.pathVariable("id");

        return roomService.findById(id)
                .flatMap(room -> ServerResponse
                        .ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(room)))
                .switchIfEmpty(ServerResponse
                        .status(HttpStatus.NOT_FOUND)
                        .body(BodyInserters.fromValue(ErrorNotFound.error(id))));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<RoomRequest> roomRequest = request.bodyToMono(RoomRequest.class);

        return roomRequest
                .flatMap(rq -> {
                    Errors errors = new BeanPropertyBindingResult(rq, RoomRequest.class.getName());
                    validator.validate(rq, errors);


                    if (errors.hasErrors()) {
                        Map<String, Object> erroresMap = new HashMap<>();
                        List<String> errorList = new ArrayList<>();
                        errors.getFieldErrors().forEach(e -> errorList.add(e.getDefaultMessage()));
                        erroresMap.put("errores", errorList);

                        return ServerResponse.badRequest().body(BodyInserters.fromValue(erroresMap));
                    } else {
                        // Aquí procesas el usuario válido
                        return roomService.save(buildDbObjet(rq, null, null))
                                .flatMap(room -> ServerResponse.created(URI.create("/room/".concat(room.getId())))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(BodyInserters.fromValue(room)));
                    }

                })
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;

                    return Mono.error(errorResponse);
                });
    }

    public Mono<ServerResponse> update(ServerRequest request) {

        String id = request.pathVariable("id");
        Mono<RoomRequest> roomRequestMono = request.bodyToMono(RoomRequest.class);

        return roomService.findById(id)
                .flatMap(room ->
                {

                    Errors errors = new BeanPropertyBindingResult(roomRequestMono, RoomRequest.class.getName());
                    validator.validate(roomRequestMono, errors);


                    if (errors.hasErrors()) {
                        Map<String, Object> erroresMap = new HashMap<>();
                        List<String> errorList = new ArrayList<>();
                        errors.getFieldErrors().forEach(e -> errorList.add(e.getDefaultMessage()));
                        erroresMap.put("errores", errorList);

                        return ServerResponse.badRequest().body(BodyInserters.fromValue(erroresMap));
                    } else {
                        return roomRequestMono
                                .flatMap(rq -> roomService.updated(buildDbObjet(rq, id, room)))
                                .flatMap(roomDb -> ServerResponse
                                        .created(URI.create("/room/".concat(roomDb.getId())))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(BodyInserters.fromValue(roomDb)));
                    }

                })
                .switchIfEmpty(ServerResponse
                        .status(HttpStatus.NOT_FOUND)
                        .body(BodyInserters.fromValue(ErrorNotFound.error(id))))
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;

                    return Mono.error(errorResponse);
                });


    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return roomService.delete(id).then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getAllTypes(ServerRequest request) {


        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(roomService.findAllRoomTypes(), RoomType.class);

    }

    public Room buildDbObjet(RoomRequest roomRequest, String id, Room room) {

        return Room.builder()
                .id(StringUtils.isEmpty(id) ? null : id)
                .roomNumber(roomRequest.getRoomNumber())
                .floor(roomRequest.getFloor()).HotelId(roomRequest.getHotelId())
                .maxGuest(roomRequest.getMaxGuest())
                .description(roomRequest.getDescription())
                .createdAt(StringUtils.isEmpty(id) ? LocalDateTime.now() : room.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .roomType(RoomType.builder()
                        .id(roomRequest.getRoomTypeRequest().getId())
                        .name(roomRequest.getRoomTypeRequest().getName()).build())
                .build();

    }


}
