package com.example.bsms.models.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "schedules")
public class Schedules extends Common {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Buses bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Routes route;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "expected_time_of_travel")
    private Integer expectedTimeOfTravelInHours;

    @Column(name = "working_days")
    private String workingDays;
}
