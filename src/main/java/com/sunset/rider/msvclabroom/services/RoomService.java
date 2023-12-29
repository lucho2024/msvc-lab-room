package com.sunset.rider.msvclabroom.services;

import com.sunset.rider.msvclabroom.models.document.Room;
import com.sunset.rider.msvclabroom.models.document.RoomType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoomService {

    Flux<Room> findAll();

    Mono<Room> findById(String id);

    Mono<Room> save(Room room);

    Mono<Room> updated(Room room);

    Mono<Void> delete(String id);

    Flux<RoomType> findAllRoomTypes();

    Flux<Room> findByHotelId(String id);
}
