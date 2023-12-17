package com.example.bsms.models;

import lombok.Data;

@Data
public class BusStopFilters {
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private Boolean isActive;
}
