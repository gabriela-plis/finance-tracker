package com.financetracker.app.expense

import com.financetracker.app.report.types.DateInterval
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalDate

import static com.financetracker.app.expense.ExpenseOperationsPerformer.*

class ExpenseOperationsPerformerTest extends Specification {

    def "should get total expenses"() {
        when:
        BigDecimal result = getTotalExpenses(expenses)

        then:
        result == expected

        where:
        expenses       | expected
        getExpenses()  | 231.44
        getEmptyList() | 0
    }

    def "should throw IllegalArgumentException when expenses are null for get total expenses"() {
        given:
        List<ExpenseEntity> expenses = null

        when:
        getTotalExpenses(expenses)

        then:
        thrown(IllegalArgumentException)
    }

    def "should get largest expense"() {
        given:
        List<ExpenseEntity> expenses = getExpenses()

        when:
        ExpenseEntity result = getLargestExpense(expenses)

        then:
        with(result) {
            id == "1"
            price == 122.22
            date == LocalDate.of(2023, 9, 15)
        }
    }

    def "should throw IllegalStateException when list for get largest expense is empty"() {
        given:
        List<ExpenseEntity> expenses = getEmptyList()

        when:
        getLargestExpense(expenses)

        then:
        thrown(IllegalStateException)
    }

    def "should throw IllegalArgumentException when expenses are null for get largest expense"() {
        given:
        List<ExpenseEntity> expenses = null

        when:
        getLargestExpense(expenses)

        then:
        thrown(IllegalArgumentException)
    }

    def "should get average daily expense"() {
        given:
        int totalDays = 30

        when:
        BigDecimal result = getAverageDailyExpense(expenses, totalDays)

        then:
        result == expected

        where:
        expenses       | expected
        getExpenses()  | 7.71
        getEmptyList() | 0
    }

    def "should throw IllegalArgumentException when expenses are null for get average daily expense"() {
        given:
        List<ExpenseEntity> expenses = null
        int totalDays = 5

        when:
        getAverageDailyExpense(expenses, totalDays)

        then:
        thrown(IllegalArgumentException)
    }

    def "should get average weekly expense"() {
        when:
        BigDecimal result = getAverageWeeklyExpense(expenses)

        then:
        result == expected

        where:
        expenses       | expected
        getExpenses()  | 57.86
        getEmptyList() | 0
    }

    def "should throw IllegalArgumentException when expenses are null for get average weekly expense"() {
        given:
        List<ExpenseEntity> expenses = null

        when:
        getAverageWeeklyExpense(expenses)

        then:
        thrown(IllegalArgumentException)
    }

    def "should get week with highest expenses"() {
        given:
        List<ExpenseEntity> expenses = getExpenses()

        when:
        DateInterval result = getWeekWithHighestExpenses(expenses)

        then:
        with(result) {
            startDate() == LocalDate.of(2023, 9, 11)
            endDate() == LocalDate.of(2023, 9, 17)
        }
    }

    def "should throw IllegalStateException when list for get week with highest expenses is empty"() {
        given:
        List<ExpenseEntity> expenses = getEmptyList()

        when:
        getWeekWithHighestExpenses(expenses)

        then:
        thrown(IllegalStateException)
    }

    def "should throw IllegalArgumentException when expenses are null for get week with highest expenses"() {
        given:
        List<ExpenseEntity> expenses = null

        when:
        getWeekWithHighestExpenses(expenses)

        then:
        thrown(IllegalArgumentException)
    }

    def "should get day with highest average expense"() {
        when:
        DayOfWeek result = getDayWithHighestAverageExpense(expenses)

        then:
        result == expected

        where:
        expenses       | expected
        getExpenses()  | DayOfWeek.FRIDAY
    }

    def "should throw IllegalStateException when list for get day with highest average expense is empty"() {
        given:
        List<ExpenseEntity> expenses = getEmptyList()

        when:
        getDayWithHighestAverageExpense(expenses)

        then:
        thrown(IllegalStateException)
    }

    def "should throw IllegalArgumentException when expenses are null for get day with highest average expense"() {
        given:
        List<ExpenseEntity> expenses = null

        when:
        getDayWithHighestAverageExpense(expenses)

        then:
        thrown(IllegalArgumentException)
    }

    private List<ExpenseEntity> getExpenses() {
        return List.of(
            getExpense(LocalDate.of(2023, 9, 1), 33.45),
            getExpense(LocalDate.of(2023, 9, 14), 65.77),
            getExpense(LocalDate.of(2023, 9, 15), 122.22),
            getExpense(LocalDate.of(2023, 9, 19), 10.0)
        )
    }

    private ExpenseEntity getExpense(LocalDate date, BigDecimal price) {
        return new ExpenseEntity("1", date, null, price, null)
    }

    private List<ExpenseEntity> getEmptyList() {
        return List.of()
    }
}
