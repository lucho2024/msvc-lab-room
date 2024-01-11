package com.sunset.rider.lab.msvclabroom.models.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Setter
@Jacksonized
public class RoomTypeRequest
{

    private String id;

    private String name;
}
