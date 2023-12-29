package com.sunset.rider.msvclabroom.repository;

import com.sunset.rider.msvclabroom.models.document.Room;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RoomRepository extends ReactiveMongoRepository<Room, String> {

    Flux<Room> findByHotelId(String hotelId);
}
