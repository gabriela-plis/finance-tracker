package com.financetracker.app.category

import com.financetracker.app.config.IntegrationTestConfig
import com.financetracker.app.security.authentication.CustomUserDetails
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

import static groovy.json.JsonOutput.toJson
import static org.hamcrest.Matchers.containsInAnyOrder
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user

@AutoConfigureMockMvc
@EnableSharedInjection
@WithMockUser
class CategoryIntegrationTest extends IntegrationTestConfig {

    @Autowired
    MockMvc mvc

    @Autowired
    CategoryRepository categoryRepository

    @Shared
    @Autowired
    UserRepository userRepository

    def setupSpec() {
        userRepository.save(getUser())
    }

    def setup() {
        categoryRepository.saveAll(getCategories())
    }

    def cleanup() {
        categoryRepository.deleteAll()
    }

    def "should return 200 (OK) and all user categories"() {
        given:
        CustomUserDetails userDetails = getUserDetails()

        when:
        def result = mvc
            .perform(get("/users/me/categories")
                .with(user(userDetails)))
            .andDo(print())

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.[*].id', containsInAnyOrder("1", "2", "3")))
        result.andExpect(jsonPath('$.[*].name', containsInAnyOrder("Food", "Healthcare", "Transportation")))
    }

    def "should return 200 (OK) and category"() {
        given:
        String userId = "1"
        CustomUserDetails userDetails = getUserDetails()

        when:
        def result = mvc
            .perform(get("/users/me/categories/$userId")
                .with(user(userDetails)))
            .andDo(print())

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.id').value(1))
        result.andExpect(jsonPath('$.name').value("Food"))
    }

    def "should return 404 (NOT FOUND) when requesting category was not found"() {
        given:
        String userId = "4"
        CustomUserDetails userDetails = getUserDetails()

        when:
        def result = mvc
            .perform(get("/users/me/categories/$userId")
                .with(user(userDetails)))
            .andDo(print())

        then:
        result.andExpect(status().isNotFound())
    }

    def "should return 201 (CREATED) when category is created"() {
        given:
        LinkedHashMap<String, Serializable> categoryToAdd = [
            name: "food"
        ]
        CustomUserDetails userDetails = getUserDetails()

        when:
        def result = mvc
            .perform(post("/users/me/categories")
                .contentType(APPLICATION_JSON)
                .content(toJson(categoryToAdd))
                .accept(APPLICATION_JSON)
                .with(user(userDetails)))
            .andDo(print())

        then:
        result.andExpect(status().isCreated())
    }

    @Unroll
    def "should return 422 (UNPROCESSABLE ENTITY) when category to add fail validation"() {
        given:
        LinkedHashMap<String, Serializable> categoryToAdd = [
            name: categoryName
        ]

        when:
        def result = mvc
            .perform(post("/users/me/categories")
                .contentType(APPLICATION_JSON)
                .content(toJson(categoryToAdd))
                .accept(APPLICATION_JSON))
            .andDo(print())

        then:
        result.andExpect(status().isUnprocessableEntity())

        where:
        categoryName << ["", " ", null]
    }

    def "should return 204 (NO CONTENT) when category is deleted"() {
        given:
        String categoryId = "1"
        CustomUserDetails userDetails = getUserDetails()

        when:
        def result = mvc
            .perform(delete("/users/me/categories/$categoryId")
                .with(user(userDetails)))
            .andDo(print())

        then:
        result.andExpect(status().isNoContent())
    }


    private List<Category> getCategories() {
        return List.of(
            new Category("1", "Food", List.of(getUser())),
            new Category("2", "Healthcare", List.of(getUser())),
            new Category("3", "Transportation", List.of(getUser())),
        )
    }

    private User getUser() {
        return new User("1", "anne", "anne@gmail.com", "anne123", List.of(Role.USER))
    }

    private CustomUserDetails getUserDetails() {
        return new CustomUserDetails("1", "abc", List.of(Role.USER))
    }
}
