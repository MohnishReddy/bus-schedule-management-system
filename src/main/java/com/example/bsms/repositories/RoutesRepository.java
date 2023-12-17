package com.example.bsms.repositories;

import com.example.bsms.models.entities.Routes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoutesRepository extends JpaRepository<Routes, Integer> {
    Optional<Routes> findOneByName(String routeName);
}
