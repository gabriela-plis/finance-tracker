package com.financetracker.app.expense

import com.financetracker.app.category.CategoryEntity
import spock.lang.Specification

import java.time.LocalDate

class ExpenseServiceTest extends Specification {

    ExpenseRepository expenseRepository = Mock()

    ExpenseService expenseService = new ExpenseService(expenseRepository)

    def "should return expenses from date interval"() {
        given:
        def startDate = LocalDate.of(2023, 1, 1)
        def endDate = LocalDate.of(2023, 1, 2)
        String userId = 1

        List<ExpenseEntity> expenses = List.of(
            new ExpenseEntity("1", LocalDate.of(2023, 1, 1), new CategoryEntity("1", "Food", null), 126.22, null)
        )

        when:
        List<ExpenseEntity> result = expenseService.getExpensesFromDateInterval(startDate, endDate, userId)

        then:
        1 * expenseRepository.findByDateBetweenAndUserId(startDate, endDate, userId) >> expenses

        and:
        with(result[0]) {
            id == "1"
            date == LocalDate.of(2023, 1, 1)
            price == 126.22
            with(category) {
                id == "1"
                name == "Food"
            }
        }
    }

}
