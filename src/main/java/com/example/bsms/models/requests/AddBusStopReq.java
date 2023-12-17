package com.example.bsms.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddBusStopReq {
    @JsonProperty("bus_stop_name")
    private String name;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("pin_code")
    private String pinCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("latitude")
    private Float latitude;

    @JsonProperty("longitude")
    private Float longitude;
}
