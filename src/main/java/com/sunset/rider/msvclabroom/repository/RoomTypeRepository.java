package com.sunset.rider.msvclabroom.repository;

import com.sun.jdi.StringReference;
import com.sunset.rider.msvclabroom.models.document.RoomType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends ReactiveMongoRepository<RoomType, String> {
}
