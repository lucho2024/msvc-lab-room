package com.sunset.rider.msvclabroom;

import com.sunset.rider.msvclabroom.models.document.Room;
import com.sunset.rider.msvclabroom.models.document.RoomType;
import com.sunset.rider.msvclabroom.repository.RoomRepository;
import com.sunset.rider.msvclabroom.repository.RoomTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Slf4j
public class MsvcLabRoomApplication implements CommandLineRunner {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MsvcLabRoomApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        mongoTemplate.dropCollection("rooms").subscribe();
        mongoTemplate.dropCollection("roomsType").subscribe();

        RoomType vip = RoomType.builder().name("vip").build();
        RoomType noVip = RoomType.builder().name("noVip").build();

        Flux.just(vip, noVip)
                .flatMap(rt -> roomTypeRepository.save(rt))
                .doOnNext(rt -> log.info("insert room type : " + rt.getId()))
                .thenMany(Flux.just(

                        Room.builder().roomNumber(1).roomType(vip).description("suite").HotelId("sdsads1111")
                                .maxGuest(5).build(),
                        Room.builder().roomNumber(1).roomType(noVip).description("normal").HotelId("dsds1111")
                                .maxGuest(4).build()

                ).flatMap(r -> roomRepository.save(r)))
                .subscribe(r -> log.info("Insert room : " + r.getId()));


    }
}
