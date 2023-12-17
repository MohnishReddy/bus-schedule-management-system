package com.example.bsms.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddRouteReq {
    @JsonProperty("route_name")
    private String name;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("pin_code")
    private String pinCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("bus_stop_names_list")
    private List<String> busStopNamesList;
}
