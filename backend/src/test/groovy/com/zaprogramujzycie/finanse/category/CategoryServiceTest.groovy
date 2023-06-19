package com.zaprogramujzycie.finanse.category

import com.zaprogramujzycie.finanse.security.authorization.Role
import com.zaprogramujzycie.finanse.user.User
import com.zaprogramujzycie.finanse.user.UserDTO
import com.zaprogramujzycie.finanse.user.UserService
import com.zaprogramujzycie.finanse.utils.exception.DocumentNotFoundException
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
        List<CategoryDTO> expected = List.of(
                new CategoryDTO("1", "Food"),
                new CategoryDTO("2", "Healthcare"),
                new CategoryDTO("3", "Transportation"),
        )

        List<Category> categoryEntities = List.of(
                new Category("1", "Food", getUserEntity()),
                new Category("2", "Healthcare", getUserEntity()),
                new Category("3", "Transportation", getUserEntity()),
        )

        when:
        List<CategoryDTO> result = categoryService.getUserCategories(userId)

        then:
        1 * categoryRepository.findByOwner_Id(userId) >> categoryEntities

        and:
        result == expected

    }

    def"should get category by id"() {
        given:
        String categoryId = "1"
        CategoryDTO expected = new CategoryDTO("1", "Food")

        when:
        CategoryDTO result = categoryService.getCategory(categoryId)

        then:
        1 * categoryRepository.findById(categoryId) >> Optional.of(new Category("1", "Food", getUserEntity()))

        and:
        result == expected
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

        userService.getUserById(userId) >> getUserEntity() //not need mock?

        when:
        categoryService.add(userId, category)

        then:
        1 * userService.getUserById(userId)
        1 * categoryRepository.insert(_ as Category)
    }

    private User getUserEntity() {
        return new User("1", "Anne", "Smith", "anne123", List.of(Role.USER))
    }
}
