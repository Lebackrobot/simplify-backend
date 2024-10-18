package com.build.twitter_backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.build.twitter_backend.models.UserModel;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    public String generateToken(UserModel user) throws JWTCreationException {
        try {
            var algorithm = Algorithm.HMAC256("secret");

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

    public String getSubject(String jwtToken) {
        try {
            var algorithm = Algorithm.HMAC256("secret");
            return JWT.require(algorithm)
                    .withIssuer("twitter-backend")
                    .build()
                    .verify(jwtToken)
                    .getSubject();
        }

        catch (JWTVerificationException error) {
            throw new RuntimeException(error);
        }
    }
}
