package com.example.bsms.models;

import lombok.Data;

import java.util.List;

@Data
public class RouteDetails {
    private String name;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private List<BusStopDetails> busStopDetailsList;
}
