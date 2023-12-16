package com.example.bsms.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bus_stops")
public class BusStops extends LocationCommons {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "is_active")
    private boolean isActive;
}
