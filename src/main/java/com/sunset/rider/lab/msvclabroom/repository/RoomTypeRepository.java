package com.sunset.rider.lab.msvclabroom.repository;

import com.sunset.rider.lab.msvclabroom.models.document.RoomType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends ReactiveMongoRepository<RoomType, String> {
}
