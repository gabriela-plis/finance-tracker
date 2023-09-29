package com.financetracker.app.user

import com.financetracker.app.config.IntegrationTestConfig
import com.financetracker.app.security.authorization.Role
import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared

import static com.financetracker.app.user.UserCorrectData.*
import static groovy.json.JsonOutput.toJson
import static org.hamcrest.Matchers.hasSize
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@EnableSharedInjection
class UserIntegrationTest extends IntegrationTestConfig {

    @Autowired
    MockMvc mvc

    @Shared
    @Autowired
    UserRepository userRepository

    def setup() {
        userRepository.saveAll(getUsers())
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    @WithMockUser(roles = "ADMIN")
    def "should return 200 (OK) and paged users"() {
        when:
        def result = mvc
            .perform(get("/users"))
            .andDo(print())

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.totalPages').value(1))
        result.andExpect(jsonPath('$.currentPage').value(0))
        result.andExpect(jsonPath('$.users[*]', hasSize(2)))
        result.andExpect(jsonPath('$.users[0].id').value("1"))
        result.andExpect(jsonPath('$.users[0].username').value("anne"))
        result.andExpect(jsonPath('$.users[0].email').value("anne@gmail.com"))
        result.andExpect(jsonPath('$.users[0].roles[0]').value("USER"))
        result.andExpect(jsonPath('$.users[1].id').value("2"))
        result.andExpect(jsonPath('$.users[1].username').value("matthew"))
        result.andExpect(jsonPath('$.users[1].email').value("matthew@gmail.com"))
        result.andExpect(jsonPath('$.users[1].roles[0]').value("USER"))
    }

    @WithMockUser(roles = "ADMIN")
    def "should return 200 (OK) and user"() {
        given:
        String userId = "1"

        when:
        def result = mvc
            .perform(get("/users/$userId"))
            .andDo(print())

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$..id').value("1"))
        result.andExpect(jsonPath('$..username').value("anne"))
        result.andExpect(jsonPath('$.email').value("anne@gmail.com"))
        result.andExpect(jsonPath('$.roles[0]').value("USER"))
    }

    @WithMockUser(roles = "ADMIN")
    def "should return 404 (NOT FOUND) when requesting user was not found"() {
        given:
        String userId = "3"

        when:
        def result = mvc
            .perform(get("/users/$userId"))
            .andDo(print())

        then:
        result.andExpect(status().isNotFound())
    }

    @WithMockUser(roles = "ADMIN")
    def "should return 200 (OK) when user is updated"() {
        given:
        String userId = "1"
        LinkedHashMap<String, Serializable> userToUpdate = [
            id      : correctId,
            username: correctUsername,
            email   : correctEmail,
            roles   : correctRoles
        ]

        when:
        def result = mvc
            .perform(put("/users/$userId")
                .contentType(APPLICATION_JSON)
                .content(toJson(userToUpdate))
                .accept(APPLICATION_JSON))
            .andDo(print())

        then:
        result.andExpect(status().isOk())
    }

    @WithMockUser(roles = "ADMIN")
    def "should return 409 (CONFLICT) when ID user to update doesn't match ID in the URL"() {
        given:
        String userId = "2"
        LinkedHashMap<String, Serializable> userToUpdate = [
            id      : correctId,
            username: correctUsername,
            email   : correctEmail,
            roles   : correctRoles
        ]

        when:
        def result = mvc
            .perform(put("/users/$userId")
                .contentType(APPLICATION_JSON)
                .content(toJson(userToUpdate))
                .accept(APPLICATION_JSON))
            .andDo(print())

        then:
        result.andExpect(status().isConflict())
    }

    @WithMockUser(roles = "ADMIN")
    def "should return 422 (UNPROCESSABLE ENTITY) when #scenario of user to update fail validation"() {
        given:
        String userId = "2"
        LinkedHashMap<String, Serializable> userToUpdate = [
            id      : id,
            username: username,
            email   : email,
            roles   : roles
        ]

        when:
        def result = mvc
            .perform(put("/users/$userId")
                .contentType(APPLICATION_JSON)
                .content(toJson(userToUpdate))
                .accept(APPLICATION_JSON))
            .andDo(print())

        then:
        result.andExpect(status().isUnprocessableEntity())

        where:
        id        | username                          | email             | roles        | scenario
        null      | correctUsername                   | correctEmail      | correctRoles | "null id"
        " "       | correctUsername                   | correctEmail      | correctRoles | "whitespace id"
        correctId | null                              | correctEmail      | correctRoles | "null username"
        correctId | " "                               | correctEmail      | correctRoles | "whitespace username"
        correctId | "an"                              | correctEmail      | correctRoles | "too short (<3 characters) username"
        correctId | "aRSjQxG4mUNYcqBgSjiFtiGzmDypxgV" | correctEmail      | correctRoles | "too long (>30 characters) username"
        correctId | correctUsername                   | null              | correctRoles | "null email"
        correctId | correctUsername                   | "@gmail.com"      | correctRoles | "no local part email"
        correctId | correctUsername                   | " @gmail.com"     | correctRoles | "only whitespace local part email"
        correctId | correctUsername                   | ".anne@gmail.com" | correctRoles | "starts from dot local part email"
        correctId | correctUsername                   | "@gmail.com"      | correctRoles | "no local part email"
        correctId | correctUsername                   | "annegmail.com"   | correctRoles | "no @ symbol email"
        correctId | correctUsername                   | "anne@"           | correctRoles | "no domain part email"
        correctId | correctUsername                   | "anne@gmail"      | correctRoles | "domain part without dot email"
        correctId | correctUsername                   | correctEmail      | null         | "null roles"
    }

    @WithMockUser(roles = "ADMIN")
    def "should return 204 (NO CONTENT) when user is deleted"() {
        given:
        String userId = "1"

        when:
        def result = mvc
            .perform(delete("/users/$userId"))
            .andDo(print())

        then:
        result.andExpect(status().isNoContent())
    }

    private List<User> getUsers() {
        return List.of(
            new User("1", "anne", "anne@gmail.com", "anne123", List.of(Role.USER)),
            new User("2", "matthew", "matthew@gmail.com", "matthew123", List.of(Role.USER))
        )
    }
}
