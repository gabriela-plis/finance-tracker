package com.financetracker.app.category


import com.financetracker.app.security.authorization.Role
import com.financetracker.app.user.User
import com.financetracker.app.user.UserService
import com.financetracker.app.utils.exception.custom.DocumentNotFoundException
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class CategoryServiceTest extends Specification {

    CategoryRepository categoryRepository = Mock()
    CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper)
    UserService userService = Mock()

    CategoryService categoryService = new CategoryService(categoryRepository, categoryMapper, userService)

    def "should get all user categories"() {
        given:
        String userId = "1"

        when:
        List<Category> result = categoryService.getUserCategories(userId)

        then:
        1 * categoryRepository.findCategoriesByUserId(userId) >> getCategories()

        and:
        result == getCategories()

    }

    def "should get category by id"() {
        given:
        String categoryId = "1"
        String userId = "1"

        when:
        Category result = categoryService.getCategory(categoryId, userId)

        then:
        1 * categoryRepository.findCategoryByIdAndUserId(categoryId, userId) >> Optional.of(getCategory())

        and:
        result == getCategory()
    }

    def "should throw DocumentNotFoundException when category was not found by id"() {
        given:
        String categoryId = "1"
        String userId = "1"

        when:
        categoryService.getCategory(categoryId, userId)

        then:
        1 * categoryRepository.findCategoryByIdAndUserId(categoryId, userId) >> Optional.empty()

        and:
        thrown(DocumentNotFoundException)
    }

    def "should delete category"() {
        given:
        String categoryId = "1"
        String userId = "1"

        when:
        categoryService.deleteCategory(categoryId, userId)

        then:
        1 * categoryRepository.deleteCategoryByIdAndUserId(categoryId, userId)
    }

    def "should add category"() {
        given:
        String userId = 1
        AddCategoryDTO category = new AddCategoryDTO("Food")

        userService.getUserById(userId) >> getUser()

        when:
        categoryService.createCategory(userId, category)

        then:
        1 * userService.getUserById(userId)
        1 * categoryRepository.insert(_ as Category)
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
