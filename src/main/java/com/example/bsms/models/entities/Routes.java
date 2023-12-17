package com.example.bsms.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "routes")
public class Routes extends LocationCommons {
    @Column(name = "name")
    private String name;

    @Column(name = "bus_stops")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "bus_stop_route_map",
        joinColumns = @JoinColumn(name = "route_id"),
        inverseJoinColumns = @JoinColumn(name = "bus_stop_id")
    )
    private Set<BusStops> busStops;
}
