package com.financetracker.app.category


import com.financetracker.app.security.authorization.Role
import com.financetracker.app.user.User
import com.financetracker.app.user.UserService
import com.financetracker.app.utils.exception.DocumentNotFoundException
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class CategoryServiceTest extends Specification {

    CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper)

    CategoryRepository categoryRepository = Mock()

    UserService userService = Mock()

    CategoryService categoryService = new CategoryService(categoryRepository, categoryMapper, userService)

    def "should get all user categories"() {
        given:
        String userId = 1

        when:
        List<Category> result = categoryService.getUserCategories(userId)

        then:
        1 * categoryRepository.findByOwner_Id(userId) >> getCategoryEntities()

        and:
        result == getCategoryEntities()

    }

    def"should get category by id"() {
        given:
        String categoryId = "1"

        when:
        Category result = categoryService.getCategory(categoryId)

        then:
        1 * categoryRepository.findById(categoryId) >> Optional.of(getCategory())

        and:
        result == getCategory()
    }

    def"should throw DocumentNotFoundException when category was not found by id"() {
        given:
        String categoryId = "1"

        when:
        categoryService.getCategory(categoryId)

        then:
        1 * categoryRepository.findById(categoryId) >> Optional.empty()

        and:
        thrown(DocumentNotFoundException)
    }

    def "should delete category"() {
        given:
        String id = 1

        when:
        categoryService.delete(id)

        then:
        1 * categoryRepository.deleteById(id)
    }

    def"should add category"() {
        given:
        String userId = 1
        AddCategoryDTO category = new AddCategoryDTO("Food")

        userService.getUserById(userId) >> getUserEntity()

        when:
        categoryService.add(userId, category)

        then:
        1 * userService.getUserById(userId)
        1 * categoryRepository.insert(_ as Category)
    }

    private Category getCategory() {
        return new Category("1", "Food", getUserEntity())
    }

    private List<Category> getCategoryEntities() {
        return List.of(
            new Category("1", "Food", getUserEntity()),
            new Category("2", "Healthcare", getUserEntity()),
            new Category("3", "Transportation", getUserEntity()),
        )
    }

    private User getUserEntity() {
        return new User("1", "Anne", "Smith", "anne123", List.of(Role.USER))
    }
}
