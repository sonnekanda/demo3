package com.example.demo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.access.lifetime}")
    private long accessTokenMinutes;

    @Value("${jwt.secret}")
    private String keyString;

    private SecretKey secretKey;

    //private static final String SECRET_KEY = "your-secret-key-which-is-very-secure";

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyString) );
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, accessTokenMinutes); // 15 минут
    }

    public String generateAccessToken(String loginName) {
        return generateAccessTokenFromUsername(loginName, accessTokenMinutes); // 15 минут
    }

//    public String generateRefreshToken(UserDetails userDetails) {
//        return generateToken(userDetails, 7 * 24 * 60); // 7 дней
//    }

    public String generateToken(UserDetails userDetails, long expirationInMinutes) {
//        return Jwts.builder()
//                .subject(userDetails.getUsername())
//                .issuedAt(new Date())
//                .expiration(Date.from(Instant.now().plus(expirationInMinutes, ChronoUnit.MINUTES)))
//                .signWith(secretKey)
//                .compact();
        return generateAccessTokenFromUsername(userDetails.getUsername(), expirationInMinutes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }

    public String generateAccessTokenFromUsername(String loginName, long expirationInMinutes) {

        return Jwts.builder()
                .subject(loginName)
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(expirationInMinutes, ChronoUnit.MINUTES)))
                .signWith(secretKey)
                .compact();

    }
}
