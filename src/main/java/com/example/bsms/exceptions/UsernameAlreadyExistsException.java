package com.example.bsms.exceptions;

import org.springframework.stereotype.Component;

@Component
public class UsernameAlreadyExistsException extends Exception {
    public final String ErrorMessage = "Username already exists!";
}
