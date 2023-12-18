package com.example.bsms.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Time;
import java.util.List;

@Data
public class AddScheduleReq {

    @JsonProperty("bus_stop_name")
    private String busStopName;

    @JsonProperty("route_name")
    private String routeName;

    @JsonProperty("start_time")
    private Time startTime;

    @JsonProperty("ett")
    private Integer expectedTimeOfJourney;

    @JsonProperty("working_days")
    List<String> workingDays;
}
