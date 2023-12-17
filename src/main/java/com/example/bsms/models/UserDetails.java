package com.example.bsms.models;

import lombok.Data;

@Data
public class UserDetails {
    private String username;
    private String name;
    private String phoneNumber;
    private String email;
    private String role;
}
