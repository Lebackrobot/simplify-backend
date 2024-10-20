package com.build.twitter_backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.build.twitter_backend.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    private Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public String generateToken(UserModel user) throws JWTCreationException {
        try {
            return JWT.create()
                    .withIssuer("twitter-backend")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        }

        catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer("twitter-backend")
                    .build()
                    .verify(token)
                    .getSubject();
        }

        catch (JWTVerificationException error) {
            throw new RuntimeException(error);
        }
    }
}
