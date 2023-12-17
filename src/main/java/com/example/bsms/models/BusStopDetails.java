package com.example.bsms.models;

import lombok.Data;

@Data
public class BusStopDetails {
    private String name;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private Float latitude;
    private Float longitude;
    private Boolean isActive;
}
