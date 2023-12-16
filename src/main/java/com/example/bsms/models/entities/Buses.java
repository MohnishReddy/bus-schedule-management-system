package com.example.bsms.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "buses")
public class Buses extends Common {
    @Column(name = "number_plate", nullable = false, unique = true)
    private String numberPlate;

    @Column(name = "bus_number", nullable = false)
    private String busNumber;

    @Column(name = "type", nullable = false)
    private String type;
}
