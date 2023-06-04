package com.zaprogramujzycie.finanse.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String jwtSecretKey = "72357538782F4125442A472D4B6150645367566B597033733676397924422645";

    public String getUsername (String token) {
//        return getClaim(token, Claims::getSubject);
        return getAllClaims(token).getSubject();
    }

//    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaims(token);
//        return claimsResolver.apply(claims);
//    }

    private Claims getAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJwt(token)
            .getBody();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }
}
