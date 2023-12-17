package com.example.bsms.repositories;

import com.example.bsms.models.entities.UserDetailsE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsE, Integer> {
    Optional<UserDetailsE> findOneByUsername(String username);
}
