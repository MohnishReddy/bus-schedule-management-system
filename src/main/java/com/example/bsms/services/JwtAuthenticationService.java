package com.example.bsms.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bsms.configurations.SystemConfigs;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.example.bsms.constants.AuthConstants.*;

@NoArgsConstructor
@AllArgsConstructor
public abstract class JwtAuthenticationService implements AuthenticationService {

    private final String JWT_ISSUER = "auth0";

    @Autowired
    private SystemConfigs systemConfigs;

    @Override
    public String createAuthorizationToken(String uniqueId) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(getJwtSecretKey());
            long newExpirationTimeMillis = System.currentTimeMillis() + AUTH_TOKEN_EXPIRY;
            return JWT.create()
                    .withIssuer(JWT_ISSUER)
                    .withClaim(AUTH_UNIQUE_ID_KEY, uniqueId)
                    .withExpiresAt(new Date(newExpirationTimeMillis))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new Exception("Could not create JWT token");
        }
    }

    @Override
    public String getUniqueIdFromToken(String token) throws Exception {
        try {
            DecodedJWT decodedJWT = verifyJwtToken(token);
            return decodedJWT.getClaim(AUTH_UNIQUE_ID_KEY).asString();
        } catch (JWTVerificationException exception){
            throw new Exception("Error in validating JWT token");
        }
    }

    @Override
    public long getExpiresAtFromToken(String token) throws Exception {
        try {
            DecodedJWT decodedJWT = verifyJwtToken(token);
            return decodedJWT.getExpiresAt().getTime();
        } catch (JWTVerificationException exception){
            throw new Exception("Error in validating JWT token");
        }
    }

    @Override
    public String refreshToken(String token) throws Exception {
        DecodedJWT decodedJWT = verifyJwtToken(token);
        Date now = new Date();
        Date expiresAt = decodedJWT.getExpiresAt();
        long minutesDiff = TimeUnit.MILLISECONDS.toMinutes(expiresAt.getTime() - now.getTime());
        if( minutesDiff < 20 )
            return createAuthorizationToken(decodedJWT.getClaim(AUTH_UNIQUE_ID_KEY).toString());
        return token;
    }

    private DecodedJWT verifyJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(getJwtSecretKey());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(JWT_ISSUER)
                .build();
        return verifier.verify(token);
    }

    private String getJwtSecretKey() {
        return systemConfigs.getJwtSecretKey();
    }
}
