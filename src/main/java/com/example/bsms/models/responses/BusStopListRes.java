package com.example.bsms.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BusStopListRes {
    @JsonProperty("bus_stops_list")
    private List<String> busStopList;
}
