package com.financetracker.app.category

import com.financetracker.app.config.MvcTestsConfig
import com.financetracker.app.security.authentication.AuthenticationService
import com.financetracker.app.security.authorization.Role
import com.financetracker.app.user.User
import com.financetracker.app.utils.exception.custom.DocumentNotFoundException
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.core.Authentication
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Unroll

import static groovy.json.JsonOutput.*
import static org.hamcrest.Matchers.*
import static org.springframework.http.MediaType.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.mapstruct.factory.Mappers.getMapper
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = CategoryController.class)
@WithMockUser
class CategoryControllerTest extends MvcTestsConfig {

    @Autowired
    MockMvc mvc

    @SpringBean
    CategoryService categoryService = Mock()

    @SpringBean
    CategoryMapper categoryMapper = Spy(getMapper(CategoryMapper))

    @SpringBean
    AuthenticationService authenticationService = Mock()

    def "should return 200 (OK) and all user categories"() {
        given:
        String userId = "1"

        when:
        def result = mvc
            .perform(get("/users/me/categories"))
            .andDo(print())

        then:
        1 * authenticationService.getUserId(_ as Authentication) >> userId
        1 * categoryService.getUserCategories(userId) >> getCategories()
        1 * categoryMapper.toDTOs(_ as List<Category>)

        and:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.[*].id', containsInAnyOrder("1", "2", "3")))
        result.andExpect(jsonPath('$.[*].name', containsInAnyOrder("Food", "Healthcare", "Transportation")))
    }

    def "should return 200 (OK) and category"() {
        given:
        String categoryId = "1"
        String userId = "1"

        when:
        def result = mvc
            .perform(get("/users/me/categories/$userId"))
            .andDo(print())

        then:
        1 * authenticationService.getUserId(_ as Authentication) >> userId
        1 * categoryService.getCategory(categoryId, userId) >> getCategory()
        1 * categoryMapper.toDTO(_ as Category)

        and:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.id').value(1))
        result.andExpect(jsonPath('$.name').value("Food"))
    }

    def "should return 404 (NOT FOUND) when requesting category was not found"() {
        given:
        String categoryId = "1"
        String userId = "1"

        when:
        def result = mvc
            .perform(get("/users/me/categories/$userId"))
            .andDo(print())

        then:
        1 * authenticationService.getUserId(_ as Authentication) >> userId
        1 * categoryService.getCategory(categoryId, userId) >> { throw new DocumentNotFoundException() }
        0 * categoryMapper.toDTO(_ as Category)

        and:
        result.andExpect(status().isNotFound())
    }

    def "should return 201 (CREATED) when category is created"() {
        given:
        LinkedHashMap<String, Serializable> categoryToAdd = [
            name: "food"
        ]

        String userId = "1"

        when:
        def result = mvc
            .perform(post("/users/me/categories")
                .contentType(APPLICATION_JSON)
                .content(toJson(categoryToAdd))
                .accept(APPLICATION_JSON))
            .andDo(print())

        then:
        1 * authenticationService.getUserId(_ as Authentication) >> userId
        1 * categoryService.createCategory(userId, _ as AddCategoryDTO)

        and:
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
        String userId = "1"

        when:
        def result = mvc
            .perform(delete("/users/me/categories/$categoryId"))
            .andDo(print())

        then:
        1 * authenticationService.getUserId(_ as Authentication) >> userId
        1 * categoryService.deleteCategory(categoryId, userId)

        and:
        result.andExpect(status().isNoContent())
    }


    private Category getCategory() {
        return new Category("1", "Food", getUser())
    }

    private List<Category> getCategories() {
        return List.of(
            new Category("1", "Food", getUser()),
            new Category("2", "Healthcare", getUser()),
            new Category("3", "Transportation", getUser()),
        )
    }

    private User getUser() {
        return new User("1", "Anne", "anne@gmail.com", "anne123", List.of(Role.USER))
    }
}
