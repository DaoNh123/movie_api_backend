package com.example.backend_sem2.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    //  using a random String
    private static final String SECRET = "gddpG8sFsyyGMi6hAwsC8plPI0XdxMUK1ur9XLlHiGHRLdnGmfudZk0eeHwOqvMz";

    // Create JWT depending on "username", we also use more param if we want
    public String generateToken(String username) {
        Map<String, Object> claims =  new HashMap<>();

        // Add more information in "claims"
        return createToken(claims, username);
    }

    /*  We can think about create new Token for Client each time Client using old Token to get data */
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // expire after 30 minutes from created
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, getSignKey())
                .compact();
    }

    // create a Sign Key from "SECRET" variable
    private Key getSignKey () {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract information from JWT
    private Claims extractAllClaims (String token){
        return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
    }

    // extract information of 1 claim
    public <T> T extractClaim (String token, Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    // Check expire time from JWT
    public Date extractExpiration (String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken (String token, UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired((token)));
    }
}
