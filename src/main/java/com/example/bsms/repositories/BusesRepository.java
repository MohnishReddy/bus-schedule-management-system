package com.example.bsms.repositories;

import com.example.bsms.models.entities.Buses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusesRepository extends JpaRepository<Buses, Integer> {
    List<Buses> findByBusNumber(String busNumber);

    Optional<Buses> findByNumberPlate(String numberPlate);

    void deleteAllByBusNumber(String busNumber);
}
