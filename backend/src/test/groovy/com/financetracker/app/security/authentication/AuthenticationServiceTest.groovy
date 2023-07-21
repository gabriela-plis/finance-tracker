package com.financetracker.app.security.authentication

import com.financetracker.app.user.UserService
import com.financetracker.app.utils.exception.UserAlreadyExistException
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import spock.lang.Specification

class AuthenticationServiceTest extends Specification{

    JwtService jwtService = Mock()
    UserService userService = Mock()
    AuthenticationManager authenticationManager = Mock()

    AuthenticationService authenticationService = new AuthenticationService(jwtService, userService, authenticationManager)

    def"should login user and return response cookie"() {
        given:
        LoginDetailsDTO loginDetails = new LoginDetailsDTO("anne@gmail.com", "anne123")
        ResponseCookie responseCookie = ResponseCookie.from("jwtToken").build()
        Authentication authentication = GroovyMock()

        when:
        ResponseCookie result = authenticationService.loginUser(loginDetails)

        then:
        1 * authenticationManager.authenticate(_ as UsernamePasswordAuthenticationToken) >> authentication
        1 * jwtService.generateTokenCookie(_ as CustomUserDetails) >> responseCookie

        and:
        result == responseCookie

    }

    def"should throw BadCredentialsException when user with matched login data doesn't exist"() {
        given:
        LoginDetailsDTO loginDetails = new LoginDetailsDTO("anne@gmail.com", "anne123")

        when:
        authenticationService.loginUser(loginDetails)

        then:
        1 * authenticationManager.authenticate(_ as UsernamePasswordAuthenticationToken) >> { throw new BadCredentialsException("") }

        and:
        thrown(BadCredentialsException)
    }

    def"should register user"() {
        given:
        RegisterDetailsDTO registerDetails = new RegisterDetailsDTO("anne", "anne@gmail.com", "anne123")

        when:
        authenticationService.registerUser(registerDetails)

        then:
        1 * userService.userIsExist(registerDetails.email()) >> true
        1 * userService.registerUser(registerDetails)
    }

    def"should throw UserAlreadyExistException during registration if given email already exist in database"() {
        given:
        RegisterDetailsDTO registerDetails = new RegisterDetailsDTO("anne", "anne@gmail.com", "anne123")

        when:
        authenticationService.registerUser(registerDetails)

        then:
        1 * userService.userIsExist(registerDetails.email()) >> false

        and:
        thrown(UserAlreadyExistException)
    }

}