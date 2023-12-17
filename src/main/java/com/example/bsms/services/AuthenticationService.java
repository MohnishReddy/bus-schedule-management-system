package com.example.bsms.services;

public interface AuthenticationService {
    String createAuthorizationToken(String uniqueId) throws Exception;
    String getUniqueIdFromToken(String token) throws Exception;
    long getExpiresAtFromToken(String token) throws Exception;
    String refreshToken(String token) throws Exception;
    String handleLogin(String username, String password) throws Exception;
    String handleRegistration(String username, String password) throws Exception;
    String getRoleFromUsername(String username) throws Exception;
}
