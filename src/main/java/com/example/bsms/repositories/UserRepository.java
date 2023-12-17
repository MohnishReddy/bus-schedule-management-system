package com.example.bsms.repositories;

import com.example.bsms.models.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findOneByUserName(String username);
    Optional<Users> findByUserNameAndHashedPassword(String username, String password);
}
