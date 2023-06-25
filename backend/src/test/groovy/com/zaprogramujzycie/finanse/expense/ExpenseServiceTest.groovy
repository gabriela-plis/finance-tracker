package com.zaprogramujzycie.finanse.expense

import com.zaprogramujzycie.finanse.category.Category
import com.zaprogramujzycie.finanse.category.CategoryDTO
import com.zaprogramujzycie.finanse.category.CategoryMapper
import com.zaprogramujzycie.finanse.security.authorization.Role
import com.zaprogramujzycie.finanse.user.User
import com.zaprogramujzycie.finanse.user.UserService
import com.zaprogramujzycie.finanse.utils.converter.StringListToObjectIdListConverter
import com.zaprogramujzycie.finanse.utils.exception.DocumentNotFoundException
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import java.time.LocalDate

class ExpenseServiceTest extends Specification {

    CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper)

    ExpenseMapper expenseMapper = Mappers.getMapper(ExpenseMapper).tap {
        expenseMapper -> expenseMapper.categoryMapper = this.categoryMapper
    }

    ExpenseRepository expenseRepository = Mock()

    UserService userService = Mock()

    StringListToObjectIdListConverter converter = Mock()

    ExpenseService expenseService = new ExpenseService(expenseRepository, expenseMapper, userService, converter)

    def"should get all user expenses"() {
        given:
        String userId = 1
        PageRequest pageable = PageRequest.of(0, 5)

        when:
        PagedExpensesDTO result = expenseService.getUserExpenses(userId, pageable)

        then:
        1 * expenseRepository.findByUser_Id(userId, pageable) >> getPagedExpenses()

        and:
        result == getPagedExpensesDTO()
    }

    def"should get expense by id"() {
        given:
        String expenseId = 1

        when:
        ExpenseDTO result = expenseService.getExpense(expenseId)

        then:
        1 * expenseRepository.findById(expenseId) >> Optional.of(getExpense())

        and:
        result == getExpenseDTO()
    }

    def"should throw DocumentNotFoundException when expense was not found by id"() {
        given:
        String expenseId = 1

        when:
        expenseService.getExpense(expenseId)

        then:
        1 * expenseRepository.findById(expenseId) >> Optional.empty()

        and:
        thrown(DocumentNotFoundException)
    }

    def"should get user sorted expenses"() {
        given:
        String userId = 1
        PageRequest pageable = PageRequest.of(0, 5)
        ExpenseSortingCriteriaDTO sortingCriteria = new ExpenseSortingCriteriaDTO(LocalDate.of(2020, 1, 1), LocalDate.of(2022, 1, 1), BigDecimal.valueOf(50), BigDecimal.valueOf(300), List.of("1", "2"))

        when:
        PagedExpensesDTO result = expenseService.getUserSortedExpenses(userId, sortingCriteria, pageable)

        then:
        1 * expenseRepository.findByUserIdAndDateBetweenAndPriceBetweenAndCategoryIdIn(userId, sortingCriteria.dateMin(), sortingCriteria.dateMax(), sortingCriteria.priceMin(), sortingCriteria.priceMax(), converter.convert(sortingCriteria.categoryIds()), pageable) >> getPagedExpenses()

        and:
        result == getPagedExpensesDTO()
    }

    def"should create expense"() {
        given:
        String userId = 1
        AddExpenseDTO expenseToAdd = new AddExpenseDTO(LocalDate.of(2020, 1, 3), new CategoryDTO("1", "Food"), BigDecimal.valueOf(27.50))

        when:
        expenseService.createExpense(userId, expenseToAdd)

        then:
        1 * userService.getUserById(userId)
        1 * expenseRepository.insert(_ as Expense)
    }

    def"should update expense"() {
        given:
        String userId = 1
        ExpenseDTO expenseToUpdate = getExpenseDTO()

        when:
        expenseService.updateExpense(userId, expenseToUpdate)

        then:
        1 * userService.getUserById(userId)
        1 * expenseRepository.save(_ as Expense)
    }

    def"should delete expense"() {
        given:
        String expenseId = 1

        when:
        expenseService.deleteExpense(expenseId)

        then:
        1 * expenseRepository.deleteById(expenseId)
    }

    private getPagedExpensesDTO() {
        List<ExpenseDTO> expenses = List.of(
            getExpenseDTO("1"),
            getExpenseDTO("2"),
        )

        return new PagedExpensesDTO(1, 0, expenses)
    }

    private Page<Expense> getPagedExpenses() {
        List<Expense> expenses = List.of(
            getExpense("1"),
            getExpense("2")
        )

        return new PageImpl<Expense>(expenses, PageRequest.of(0, 5), 5)
    }

    private Expense getExpense() {
        return new Expense("1", LocalDate.of(2020, 1, 1), new Category("1", "Food", null), new BigDecimal(100.50), getUser())
    }

    private Expense getExpense(String id) {
        return new Expense(id, LocalDate.of(2020, 1, 1), new Category("1", "Food", null), new BigDecimal(100.50), getUser())
    }

    private ExpenseDTO getExpenseDTO() {
        new ExpenseDTO("1", LocalDate.of(2020, 1, 1), new CategoryDTO("1", "Food"), new BigDecimal(100.50))
    }

    private ExpenseDTO getExpenseDTO(String id) {
        new ExpenseDTO(id, LocalDate.of(2020, 1, 1), new CategoryDTO("1", "Food"), new BigDecimal(100.50))
    }

    private User getUser() {
        return new User("1", "Anne", "Smith", "anne123", List.of(Role.USER))
    }
}
