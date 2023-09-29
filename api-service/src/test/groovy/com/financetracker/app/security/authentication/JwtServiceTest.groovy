package com.financetracker.app.security.authentication

import com.financetracker.app.security.authorization.Role
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.http.ResponseCookie
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Clock
import java.time.Instant
import java.time.ZoneId

import static java.time.temporal.ChronoUnit.DAYS

class JwtServiceTest extends Specification {

    String secretKey = "32560f02cf765cacf7de10296261e64f236282f1457bf3335ae2292503b8aa54"
    int expirationMs = 43200000
    String cookieName = "jwt"
    Clock clock = Clock.fixed(Instant.now().plus(5, DAYS), ZoneId.of("UTC"))

    JwtService jwtService = new JwtService(secretKey, expirationMs, cookieName, clock)

    def "should get username"() {
        given:
        String token = getToken()
        String expected = "anne@gmail.com"

        when:
        String result = jwtService.getUsername(token)

        then:
        result == expected
    }

    def "should generate token cookie"() {
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

    def "should return true if token is valid"() {
        given: "not expired token with matched email"
        String token = Jwts.builder()
            .setSubject("anne@gmail.com")
            .setExpiration(getFutureDate())
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
            .compact()

        CustomUserDetails userDetails = getUserDetails()

        when:
        boolean result = jwtService.isTokenValid(token, userDetails)

        then:
        result == true
    }

    @Unroll
    def "should return false if token isn't valid - #scenario"() {
        given: "not expired token with not matched email"
        String token = Jwts.builder()
            .setSubject(subject)
            .setExpiration(expiration)
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
            .compact()

        CustomUserDetails userDetails = getUserDetails()

        when:
        boolean result = jwtService.isTokenValid(token, userDetails)

        then:
        result == false

        where:
        subject             | expiration      | scenario
        "anne123@gmail.com" | getFutureDate() | "invalid email"
        "anne@gmail.com"    | getOldDate()    | "expired token"
        "anne123@gmail.com" | getOldDate()    | "invalid email and expired token"
    }

    private int getExpirationInSeconds() {
        return expirationMs / 1000
    }

    private CustomUserDetails getUserDetails() {
        return new CustomUserDetails("anne@gmail.com", "anne123", List.of(Role.USER))
    }

    private String getToken() {
        return Jwts.builder().setSubject("anne@gmail.com").signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))).compact()
    }

    private Date getOldDate() {
        return Date.from(Instant.now().plus(1, DAYS))
    }

    private Date getFutureDate() {
        return Date.from(Instant.now().plus(7, DAYS))
    }
}
