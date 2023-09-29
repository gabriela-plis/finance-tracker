package com.financetracker.app.security.authentication

import com.financetracker.app.security.authorization.Role
import com.financetracker.app.user.User
import com.financetracker.app.user.UserService
import com.financetracker.app.utils.exception.custom.UserAlreadyExistException
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class AuthenticationServiceTest extends Specification {

    JwtService jwtService = Mock()
    UserService userService = Mock()
    PasswordEncoder passwordEncoder = Mock()

    AuthenticationService authenticationService = new AuthenticationService(jwtService, userService, passwordEncoder)

    def "should login user and return response cookie"() {
        given:
        LoginDetailsDTO loginDetails = getLoginDetails()
        ResponseCookie responseCookie = ResponseCookie.from("jwtToken").build()

        when:
        ResponseCookie result = authenticationService.loginUser(loginDetails)

        then:
        1 * userService.getUserByEmail(loginDetails.email()) >> getUser()
        1 * passwordEncoder.matches(loginDetails.password(), getUser().password) >> true
        1 * jwtService.generateTokenCookie(_ as CustomUserDetails) >> responseCookie

        and:
        result == responseCookie

    }

    def "should throw BadCredentialsException when user with matched login data doesn't exist"() {
        given:
        LoginDetailsDTO loginDetails = getLoginDetails()

        when:
        authenticationService.loginUser(loginDetails)

        then:
        1 * userService.getUserByEmail(loginDetails.email()) >> getUser()
        1 * passwordEncoder.matches(loginDetails.password(), getUser().password) >> false

        and:
        thrown(BadCredentialsException)
    }

    def "should register user"() {
        given:
        RegisterDetailsDTO registerDetails = getRegisterDetails()

        when:
        authenticationService.registerUser(registerDetails)

        then:
        1 * userService.userIsExist(registerDetails.email()) >> false
        1 * userService.registerUser(registerDetails)
    }

    def "should throw UserAlreadyExistException during registration if given email already exist in database"() {
        given:
        RegisterDetailsDTO registerDetails = getRegisterDetails()

        when:
        authenticationService.registerUser(registerDetails)

        then:
        1 * userService.userIsExist(registerDetails.email()) >> true

        and:
        thrown(UserAlreadyExistException)
    }

    def "should get user id"() {
        given:
        Authentication authentication = new UsernamePasswordAuthenticationToken(new CustomUserDetails("1", "anne123", List.of(Role.USER)), null, null)
        String expected = "1"

        when:
        String result = authenticationService.getUserId(authentication)

        then:
        result == expected
    }

    private User getUser() {
        return new User("1", "Anne", "anne@gmail.com", "anne123", List.of(Role.USER))
    }

    private RegisterDetailsDTO getRegisterDetails() {
        return new RegisterDetailsDTO("anne", "anne@gmail.com", "anne123")
    }

    private LoginDetailsDTO getLoginDetails() {
        return new LoginDetailsDTO("anne@gmail.com", "anne123")
    }
}