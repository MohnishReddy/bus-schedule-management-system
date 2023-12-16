package com.example.bsms.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users")
public class Users extends Common {
    @Column(name = "username", nullable = false, updatable = false, unique = true)
    private String userName;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "role", nullable = false)
    private String role;
}
