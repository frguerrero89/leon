package com.ngsoftware.leon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    @Value("${app.security.issuer}")
    private String issuer;
    @Value("${app.security.secret}")
    private String secret;


    public String create(String username){
        return JWT.create().withSubject(username)
        .withIssuer(this.issuer)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(5)))
        .sign(Algorithm.HMAC256(secret));
    }
}
