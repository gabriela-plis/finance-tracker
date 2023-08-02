package com.financetracker.app.security.authentication

import com.financetracker.app.config.IntegrationTestConfig
import com.financetracker.app.security.authorization.Role
import com.financetracker.app.user.User
import com.financetracker.app.user.UserRepository
import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Unroll

import static com.financetracker.app.security.authentication.AuthenticationCorrectData.correctEmail
import static com.financetracker.app.security.authentication.AuthenticationCorrectData.correctPassword
import static com.financetracker.app.security.authentication.AuthenticationCorrectData.correctUsername
import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@EnableSharedInjection
@WithMockUser
class AuthenticationIntegrationTest extends IntegrationTestConfig {

    @Autowired
    MockMvc mvc

    @Shared
    @Autowired
    UserRepository userRepository

    def setup() {
        userRepository.save(getUser())
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    def "should return 200 (OK) when user is registered"() {
        given:
        LinkedHashMap<String, Serializable> userToRegister = [
            username: "matthew",
            email   : "matthew@gmail.com",
            password: correctPassword
        ]

        when:
        def result = mvc
            .perform(post("/auth/register")
                .contentType(APPLICATION_JSON)
                .content(toJson(userToRegister))
                .accept(APPLICATION_JSON))
            .andDo(print())

        then:
        result.andExpect(status().isOk())
    }

    def "should return 409 (CONFLICT) when someone try register but user with this email already exist"() {
        given:
        LinkedHashMap<String, Serializable> userToRegister = [
            username: correctUsername,
            email   : correctEmail,
            password: correctPassword
        ]

        when:
        def result = mvc
            .perform(post("/auth/register")
                .contentType(APPLICATION_JSON)
                .content(toJson(userToRegister))
                .accept(APPLICATION_JSON))
            .andDo(print())

        then:
        result.andExpect(status().isConflict())
    }

    @Unroll
    def "should return 422 (UNPROCESSABLE ENTITY) when #scenario fail validation during registration"() {
        given:
        LinkedHashMap<String, Serializable> userToRegister = [
            username: username,
            email   : email,
            password: password
        ]

        when:
        def result = mvc
            .perform(post("/auth/register")
                .contentType(APPLICATION_JSON)
                .content(toJson(userToRegister))
                .accept(APPLICATION_JSON))
            .andDo(print())

        then:
        result.andExpect(status().isUnprocessableEntity())

        where:
        username                          | email             | password                         | scenario
        null                              | correctEmail      | correctPassword                  | "null username"
        " "                               | correctEmail      | correctPassword                  | "only white character username"
        "an"                              | correctEmail      | correctPassword                  | "too short (<3) username"
        "AnneAnneAnneAnneAnneAnneAnneAnn" | correctEmail      | correctPassword                  | "too long (>30) username"
        correctUsername                   | correctEmail      | null                             | "null password"
        correctUsername                   | correctEmail      | "Anne12!"                        | "too short (<8) password"
        correctUsername                   | correctEmail      | "anne123!Anne123anne123!Anne123" | "too long (>24) password"
        correctUsername                   | correctEmail      | "ANNE123!ANNE"                   | "no at least one lowercase letter password"
        correctUsername                   | correctEmail      | "anne123!anne"                   | "no at least one uppercase letter password"
        correctUsername                   | correctEmail      | "anne!Anne"                      | "no at least one digit password"
        correctUsername                   | correctEmail      | "anne123Anne"                    | "no at least one special password"
        correctUsername                   | correctEmail      | "anne 123! Anne"                 | "with whitespace password"
        correctUsername                   | null              | correctPassword                  | "null email"
        correctUsername                   | "@gmail.com"      | correctPassword                  | "no local part email"
        correctUsername                   | " @gmail.com"     | correctPassword                  | "only whitespace local part email"
        correctUsername                   | ".anne@gmail.com" | correctPassword                  | "starts from dot local part email"
        correctUsername                   | "@gmail.com"      | correctPassword                  | "no local part email"
        correctUsername                   | "annegmail.com"   | correctPassword                  | "no @ symbol email"
        correctUsername                   | "anne@"           | correctPassword                  | "no domain part email"
        correctUsername                   | "anne@gmail"      | correctPassword                  | "domain part without dot email"
    }

    def "should return 200 (OK) and JWT cookie in header when user is logged in"() {
        given:
        LinkedHashMap<String, Serializable> loginData = [
            email   : correctEmail,
            password: correctPassword
        ]
        String cookieName = "finance-tracker-jwt"

        when:
        def result = mvc
            .perform(post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content(toJson(loginData))
                .accept(APPLICATION_JSON))
            .andDo(print())

        then:
        result.andExpect(status().isOk())
        result.andExpect(cookie().exists(cookieName))
        result.andExpect(cookie().path(cookieName, "/auth"))
        result.andExpect(cookie().httpOnly(cookieName, true))
    }

    def "should return 401 (UNAUTHORIZED) when user is failed login"() {
        given:
        LinkedHashMap<String, Serializable> loginData = [
            email   : correctEmail,
            password: correctPassword
        ]

        when:
        def result = mvc
            .perform(post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content(toJson(loginData))
                .accept(APPLICATION_JSON))
            .andDo(print())

        then:
        result.andExpect(status().isUnauthorized())
    }

    @Unroll
    def "should return 422 (UNPROCESSABLE ENTITY) when #scenario failed validation during login"() {
        given:
        LinkedHashMap<String, Serializable> loginData = [
            email   : email,
            password: password
        ]

        when:
        def result = mvc
            .perform(post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content(toJson(loginData))
                .accept(APPLICATION_JSON))
            .andDo(print())

        then:
        result.andExpect(status().isUnprocessableEntity())

        where:
        email             | password                         | scenario
        correctEmail      | null                             | "null password"
        correctEmail      | "Anne12!"                        | "too short (<8) password"
        correctEmail      | "anne123!Anne123anne123!Anne123" | "too long (>24) password"
        correctEmail      | "ANNE123!ANNE"                   | "no at least one lowercase letter password"
        correctEmail      | "anne123!anne"                   | "no at least one uppercase letter password"
        correctEmail      | "anne!Anne"                      | "no at least one digit password"
        correctEmail      | "anne123Anne"                    | "no at least one special password"
        correctEmail      | "anne 123! Anne"                 | "with whitespace password"
        null              | correctPassword                  | "null email"
        "@gmail.com"      | correctPassword                  | "no local part email"
        " @gmail.com"     | correctPassword                  | "only whitespace local part email"
        ".anne@gmail.com" | correctPassword                  | "starts from dot local part email"
        "@gmail.com"      | correctPassword                  | "no local part email"
        "annegmail.com"   | correctPassword                  | "no @ symbol email"
        "anne@"           | correctPassword                  | "no domain part email"
        "anne@gmail"      | correctPassword                  | "domain part without dot email"
    }

    private User getUser() {
        return new User("1", correctUsername, correctEmail, "\$2a\$10\$fZHUpptv4YaONLmWBzc47eUHiSQXIKVgaBLbNGJPPamfH2XU8ZxT.", List.of(Role.USER))
    }
}
