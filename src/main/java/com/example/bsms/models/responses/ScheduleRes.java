package com.example.bsms.models.responses;

import com.example.bsms.models.BusStopDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Time;
import java.util.List;

@Data
public class ScheduleRes {
    @JsonProperty("bus_name")
    private String busName;

    @JsonProperty("route_name")
    private String routeName;

    @JsonProperty("list_of_bus_stops")
    private List<BusStopDetails> busStopDetailsList;

    @JsonProperty("start_time")
    private Time startTime;

    @JsonProperty("ett")
    private Integer expectedTimeOfTravelInHours;

    @JsonProperty("working_days")
    private List<String> workingDays;
}
