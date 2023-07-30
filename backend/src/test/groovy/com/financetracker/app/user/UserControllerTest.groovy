package com.financetracker.app.user

import com.financetracker.app.config.MvcTestsConfig
import com.financetracker.app.security.authorization.Role
import com.financetracker.app.utils.exception.custom.DocumentNotFoundException
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc

import static com.financetracker.app.user.UserControllerTest.CorrectData.*
import static groovy.json.JsonOutput.toJson
import static org.hamcrest.Matchers.hasSize
import static org.mapstruct.factory.Mappers.*
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserController])
class UserControllerTest extends MvcTestsConfig {

    @Autowired
    MockMvc mvc

    @SpringBean
    UserService userService = Mock()

    @SpringBean
    UserMapper userMapper = Spy(getMapper(UserMapper))

    @WithMockUser
    def "should return 200 (OK) and paged users"() {
        when:
        def result = mvc
            .perform(get("/users"))
            .andDo(print())

        then:
        1 * userService.getAllUsers(_ as PageRequest) >> getPagedUsers()
        1 * userMapper.toPagedDTO(_ as Page<User>)

        and:
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

    @WithMockUser
    def "should return 200 (OK) and user"() {
        given:
        String userId = "1"

        when:
        def result = mvc
            .perform(get("/users/$userId"))
            .andDo(print())

        then:
        1 * userService.getUserById(userId) >> getUser()
        1 * userMapper.toDTO(_ as User)

        and:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$..id').value("1"))
        result.andExpect(jsonPath('$..username').value("anne"))
        result.andExpect(jsonPath('$.email').value("anne@gmail.com"))
        result.andExpect(jsonPath('$.roles[0]').value("USER"))
    }

    @WithMockUser
    def "should return 404 (NOT FOUND) when requesting user was not found"() {
        given:
        String userId = "1"

        when:
        def result = mvc
            .perform(get("/users/$userId"))
            .andDo(print())

        then:
        1 * userService.getUserById(userId) >> { throw new DocumentNotFoundException() }
        0 * userMapper.toDTO(_ as User)

        and:
        result.andExpect(status().isNotFound())
    }

    @WithMockUser
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
        1 * userService.updateUser(userId, _ as UserDTO)

        and:
        result.andExpect(status().isOk())
    }

    @WithMockUser
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
        0 * userService.updateUser(userId, _ as UserDTO)

        and:
        result.andExpect(status().isConflict())
    }

    @WithMockUser
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
        0 * userService.updateUser(userId, _ as UserDTO)

        and:
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

    @WithMockUser
    def "should return 204 (NO CONTENT) when user is deleted"() {
        given:
        String userId = "1"

        when:
        def result = mvc
            .perform(delete("/users/$userId"))
            .andDo(print())

        then:
        1 * userService.deleteUser(userId)

        and:
        result.andExpect(status().isNoContent())
    }

    private Page<User> getPagedUsers() {
        List<User> users = List.of(
            new User("1", "anne", "anne@gmail.com", "anne123", List.of(Role.USER)),
            new User("2", "matthew", "matthew@gmail.com", "matthew123", List.of(Role.USER))
        )

        return new PageImpl<User>(users, PageRequest.of(0, 5), 5)
    }

    private User getUser() {
        return new User("1", "anne", "anne@gmail.com", "anne123", List.of(Role.USER))
    }

    protected static class CorrectData {
        public static final String correctId = "1"
        public static final String correctUsername = "anne"
        public static final String correctEmail = "anne@gmail.com"
        public static final String[] correctRoles = ["USER"]
    }
}
