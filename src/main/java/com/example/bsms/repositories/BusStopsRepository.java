package com.example.bsms.repositories;

import com.example.bsms.models.entities.BusStops;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusStopsRepository extends JpaRepository<BusStops, Integer> {
    Optional<BusStops> findOneByName(String busStopName);
}
