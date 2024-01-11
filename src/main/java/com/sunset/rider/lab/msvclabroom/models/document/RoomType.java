package com.sunset.rider.lab.msvclabroom.models.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roomsType")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomType {

    @Id
    private String id;

    private String name;
}
