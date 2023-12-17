package com.example.bsms.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddBusDetailsReq {
    @JsonProperty("bus_number")
    private String busNumber;

    @JsonProperty("number_plate")
    private String numberPlate;

    @JsonProperty("bus_type")
    private String busType;
}
