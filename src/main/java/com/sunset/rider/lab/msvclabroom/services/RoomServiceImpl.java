package com.sunset.rider.lab.msvclabroom.services;

import com.sunset.rider.lab.msvclabroom.repository.RoomTypeRepository;
import com.sunset.rider.lab.msvclabroom.models.document.Room;
import com.sunset.rider.lab.msvclabroom.models.document.RoomType;
import com.sunset.rider.lab.msvclabroom.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public Flux<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Mono<Room> findById(String id) {
        return roomRepository.findById(id);
    }

    @Override
    public Mono<Room> save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Mono<Room> updated(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Mono<Void> delete(String id) {
        return roomRepository.deleteById(id);
    }

    @Override
    public Flux<RoomType> findAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    @Override
    public Flux<Room> findByHotelId(String id) {
        return roomRepository.findByHotelId(id);
    }
}
