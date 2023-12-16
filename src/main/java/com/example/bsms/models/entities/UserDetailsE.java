package com.example.bsms.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "user_details")
public class UserDetailsE extends Common {
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

}
