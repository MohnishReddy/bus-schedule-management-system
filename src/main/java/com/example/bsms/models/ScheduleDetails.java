package com.example.bsms.models;

import lombok.Data;

import java.sql.Time;
import java.util.List;

@Data
public class ScheduleDetails {
    private String busName;
    private String routeName;
    private List<BusStopDetails> busStopDetailsList;
    private Time startTime;
    private Integer expectedTimeOfTravelInHours;
    private List<String> workingDays;
}
