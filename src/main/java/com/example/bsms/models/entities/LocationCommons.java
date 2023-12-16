package com.example.bsms.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class LocationCommons extends Common {
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "pin_code", nullable = false)
    private String picCode;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @Column(name = "longitude", nullable = false)
    private Float longitude;
}
