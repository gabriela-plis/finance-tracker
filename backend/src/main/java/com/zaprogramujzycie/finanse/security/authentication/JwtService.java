package com.zaprogramujzycie.finanse.security.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String jwtSecretKey = "72357538782F4125442A472D4B6150645367566B597033733676397924422645";
    private static final int jwtExpirationMs = 86400000; //24h

    public String getUsername (String token) {
        return getAllClaims(token).getSubject();
    }

    public ResponseCookie generateTokenCookie(UserDetails userDetails) {
        String token = Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();

        return ResponseCookie.from(token).path("/auth").maxAge(jwtExpirationMs).httpOnly(true).build();

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
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
}
