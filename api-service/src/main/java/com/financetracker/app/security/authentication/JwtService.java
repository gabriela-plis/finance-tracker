package com.financetracker.app.security.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${finance-tracker.jwt.secret-key}")
    private final String jwtSecretKey;

    @Value("${finance-tracker.jwt.expiration-ms}")
    private final int jwtExpirationMs;

    @Value("${finance-tracker.jwt.cookie-name}")
    private final String cookieName;

    private final Clock clock;

    public String getUsername (String token) {
        return getAllClaims(token).getSubject();
    }

    public ResponseCookie generateTokenCookie(CustomUserDetails userDetails) {
        String token = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setSubject(userDetails.getUsername())
            .setIssuedAt(Date.from(Instant.now(clock)))
            .setExpiration(Date.from(Instant.ofEpochMilli(Date.from(Instant.now(clock)).getTime() + jwtExpirationMs)))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();

        return ResponseCookie
            .from(cookieName, token)
            .path("/auth")
            .maxAge(getExpirationInSeconds())
            .httpOnly(true)
            .build();
    }

    public boolean isTokenValid(String token, CustomUserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(Date.from(Instant.now(clock)));
    }

    private Date getExpiration(String token) {
        return getAllClaims(token).getExpiration();
    }

    private Claims getAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }

    private int getExpirationInSeconds() {
        return jwtExpirationMs/1000;
    }
}
