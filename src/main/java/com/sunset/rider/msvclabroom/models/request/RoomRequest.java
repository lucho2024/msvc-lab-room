package com.sunset.rider.msvclabroom.models.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Setter
@Jacksonized
public class RoomRequest {


    @NotNull(message = "roomNumber no puede ser vacio o nulo")
    private Integer roomNumber;
    @NotEmpty(message = "description no puede ser vacio o nulo")
    private String description;
    @NotNull(message = "floor no puede ser vacio o nulo")
    private Integer floor;
    @NotNull(message = "maxGuest no puede ser vacio o nulo")
    private Integer maxGuest;

    @JsonProperty("roomType")
    @NotNull(message = "roomTypeRequest no puede ser vacio o nulo")
    private RoomTypeRequest roomTypeRequest;
    @NotEmpty(message = "hotelId no puede ser vacio o nulo")
    private String hotelId;

}
