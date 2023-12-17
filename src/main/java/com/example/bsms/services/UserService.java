package com.example.bsms.services;

import com.example.bsms.models.UserDetails;

import java.util.List;

public interface UserService {
    void addUserDetails(UserDetails userDetails) throws Exception;
    UserDetails getUserDetails(String username) throws Exception;
    void deleteUser(String username) throws Exception;
    List<UserDetails> getAllUsers();
    void updateRoleOfUser(String username, String role) throws Exception;

}
