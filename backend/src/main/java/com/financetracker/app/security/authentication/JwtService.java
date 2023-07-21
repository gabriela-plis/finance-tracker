package com.financetracker.app.security.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class JwtService {

    @Value("${finance-tracker.jwt.secret-key}")
    private String jwtSecretKey;
    @Value("${finance-tracker.jwt.expiration-ms}")
    private int jwtExpirationMs;
    @Value("${finance-tracker.jwt.cookie-name}")
    private String cookieName;

    public String getUsername (String token) {
        return getAllClaims(token).getSubject();
    }

    public ResponseCookie generateTokenCookie(CustomUserDetails userDetails) {
        String token = Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();

        return ResponseCookie.from(cookieName, token).path("/auth").maxAge(getExpirationInSeconds()).httpOnly(true).build();

    }

    public boolean isTokenValid(String token, CustomUserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
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
