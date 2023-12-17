package com.example.bsms.constants;

import org.springframework.stereotype.Component;

@Component
public class AuthConstants {
    public final static String AUTH_HEADER = "Authorization";
    public final static String AUTH_PREFIX = "Bearer";

    public final static Long AUTH_TOKEN_EXPIRY = 864_000_000L; // 10 days

    public final static String AUTH_UNIQUE_ID_KEY = "unique_id";

    public final static String CONTEXT_ROLE_KEY = "role";
    public final static String CONTEXT_USERNAME_KEY = "username";
}
