package com.codingShuttle.praj.prod_ready_features.prod_ready_features.service;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;


    //We create a default secret key, then we will get the key merged with some hmascShakeyfor function
    private SecretKey getSecreteKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //In this method we are going to take the user from our user entity and take in and create a token
    //Going to be used as access token only.
    public String generateAccessToken(User user){

        //Builder use to build token
        /*
        We add in -- user, parameter issueAt, expiration, signWith
         */
        return
            Jwts.builder()
                .subject(user.getId().toString())
                .claim("email",user.getEmail())
                .claim("roles", user.getRoles().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 50))
                .signWith(getSecreteKey())
                .compact();
    }

    //Generate access token
    public String generateRefreshToken(User user){

        return
                Jwts.builder()
                        .subject(user.getId().toString())
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 6))
                        .signWith(getSecreteKey())
                        .compact();
    }

    //Now we have to decode the token to get the user id
    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecreteKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }


}
