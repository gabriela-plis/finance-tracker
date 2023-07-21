package com.financetracker.app.security.authentication

import com.financetracker.app.security.authorization.Role
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.http.ResponseCookie
import spock.lang.Specification


class JwtServiceTest extends Specification{

    String secretKey = "32560f02cf765cacf7de10296261e64f236282f1457bf3335ae2292503b8aa54"
    int expirationMs = 43200000
    String cookieName = "jwt"

    JwtService jwtService = new JwtService(secretKey, expirationMs, cookieName)

    def"should get username"() {
        given:
        String token = getToken()
        String expected = "anne@gmail.com"

        when:
        String result = jwtService.getUsername(token)

        then:
        result == expected
    }

    def"should generate token cookie"() {
        given:
        CustomUserDetails userDetails = getUserDetails()

        when:
        ResponseCookie result = jwtService.generateTokenCookie(userDetails)

        then:
        result.name == cookieName
        result.path == "/auth"
        result.maxAge.seconds == getExpirationInSeconds()
        result.httpOnly
    }

    def"should return true if token is valid"() {
        given: "not expired token with matched email"
        String token = Jwts.builder().setSubject("anne@gmail.com").setExpiration(new Date(System.currentTimeMillis() + 3600000)).signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))).compact()
        CustomUserDetails userDetails = getUserDetails()

        when:
        boolean result = jwtService.isTokenValid(token, userDetails)

        then:
        result == true
    }

    def"should return false if token isn't valid"() {
        given: "not expired token with not matched email" //TODO create out of date token
        String token = Jwts.builder().setSubject("anne123@gmail.com").setExpiration(new Date(System.currentTimeMillis() + 3600000)).signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))).compact()
        CustomUserDetails userDetails = getUserDetails()

        when:
        boolean result = jwtService.isTokenValid(token, userDetails)

        then:
        result == false
    }

    private int getExpirationInSeconds() {
        return expirationMs/1000
    }

    private CustomUserDetails getUserDetails() {
        return new CustomUserDetails("anne@gmail.com", "anne123", List.of(Role.USER))
    }

    private String getToken() {
        return Jwts.builder().setSubject("anne@gmail.com").signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))).compact()
    }
}
