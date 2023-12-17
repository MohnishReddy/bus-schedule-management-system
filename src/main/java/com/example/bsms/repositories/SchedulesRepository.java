package com.example.bsms.repositories;

import com.example.bsms.models.entities.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulesRepository extends JpaRepository<Schedules, Integer> {
}
