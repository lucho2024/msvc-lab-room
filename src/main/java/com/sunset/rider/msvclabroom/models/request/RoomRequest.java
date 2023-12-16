package com.sunset.rider.msvclabroom.models.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Setter
@Jacksonized
public class RoomRequest {


    private Integer roomNumber;

    private String description;

    private Integer floor;

    private Integer maxGuest;

    @JsonProperty("roomType")
    private RoomTypeRequest roomTypeRequest;

    private String hotelId;

}
