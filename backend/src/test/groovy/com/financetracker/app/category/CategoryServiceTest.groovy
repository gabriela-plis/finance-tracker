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
        1 * categoryRepository.findCategoriesByUsersId(userId) >> getCategories()

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
        1 * categoryRepository.findCategoryByIdAndUsersId(categoryId, userId) >> Optional.of(getCategory())

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
        1 * categoryRepository.findCategoryByIdAndUsersId(categoryId, userId) >> Optional.empty()

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
        1 * categoryRepository.findCategoryByIdAndUsersId(categoryId, userId) >> Optional.of(getCategory())
        1 * categoryRepository.delete(_ as Category)
    }

    def "should delete user attachment from category"() {
        given:
        String categoryId = "1"
        String userId = "1"
        Category category = new Category("1", "Food", new ArrayList<>(List.of(getUser(), getUser())))

        when:
        categoryService.deleteCategory(categoryId, userId)

        then:
        1 * categoryRepository.findCategoryByIdAndUsersId(categoryId, userId) >> Optional.of(category)
        1 * userService.getUserById(userId) >> getUser()
        1 * categoryRepository.save(category)
    }

    def "should add category"() {
        given:
        String userId = 1
        AddCategoryDTO category = new AddCategoryDTO("Food")

        when:
        categoryService.createCategory(userId, category)

        then:
        1 * categoryRepository.findByName(category.name()) >> Optional.empty()
        1 * userService.getUserById(userId) >> getUser()
        1 * categoryRepository.insert(_ as Category)
    }

    def "should attach user to existing category when someone want add the same category"() {
        given:
        String userId = 1
        AddCategoryDTO categoryToAdd = new AddCategoryDTO("Food")
        Category category = new Category("1", "Food", new ArrayList<>())

        when:
        categoryService.createCategory(userId, categoryToAdd)

        then:
        1 * categoryRepository.findByName(categoryToAdd.name()) >> Optional.of(category)
        1 * userService.getUserById(userId) >> getUser()
        0 * categoryRepository.insert()
    }

    def "should stop adding category if category to add already exists and user is attached to it" () {
        given:
        String userId = 1
        AddCategoryDTO categoryToAdd = new AddCategoryDTO("Food")

        when:
        categoryService.createCategory(userId, categoryToAdd)

        then:
        1 * categoryRepository.findByName(categoryToAdd.name()) >> Optional.of(getCategory())
        0 * userService.getUserById(userId)
        0 * categoryRepository.insert(_ as Category)
    }

    private Category getCategory() {
        return new Category("1", "Food", List.of(getUser()))
    }

    private List<Category> getCategories() {
        return List.of(
            new Category("1", "Food", List.of(getUser())),
            new Category("2", "Healthcare", List.of(getUser())),
            new Category("3", "Transportation", List.of(getUser())),
        )
    }

    private User getUser() {
        return new User("1", "Anne", "anne@gmail.com", "anne123", List.of(Role.USER))
    }
}
