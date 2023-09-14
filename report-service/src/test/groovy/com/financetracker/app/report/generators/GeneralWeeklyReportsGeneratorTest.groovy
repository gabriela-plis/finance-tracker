package com.financetracker.app.report.generators

import com.financetracker.app.expense.ExpenseEntity
import com.financetracker.app.expense.ExpenseService
import com.financetracker.app.income.IncomeEntity
import com.financetracker.app.income.IncomeService
import com.financetracker.app.report.db.ReportType
import com.financetracker.app.report.db.ReportTypeService
import com.financetracker.app.report.types.GeneralWeeklyReport
import com.financetracker.app.user.Role
import com.financetracker.app.user.UserEntity
import spock.lang.Specification

import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId

class GeneralWeeklyReportsGeneratorTest extends Specification {

    IncomeService incomeService = Mock()
    ExpenseService expenseService = Mock()
    ReportTypeService reportService = Mock()
    Clock clock = Clock.fixed(LocalDate.of(2023, 9, 4).atStartOfDay(ZoneId.of("UTC")).toInstant(), ZoneId.of("UTC"))

    GeneralWeeklyReportsGenerator weeklyReportsGenerator = new GeneralWeeklyReportsGenerator(incomeService, expenseService, reportService, clock)

    def "should generate general weekly reports"() {
        given:
        ReportType type = ReportType.GENERAL_WEEKLY_REPORT
        def users = getUsers()
        def incomes = getIncomes()
        def expensesOfFirstUser = getExpensesOfFirstUser()
        def expensesOfSecondUser = getExpensesOfSecondUser()
        def expensesOfThirdUser = new ArrayList<ExpenseEntity>()

        when:
        List<GeneralWeeklyReport> result = weeklyReportsGenerator.generate()

        then:
        1 * reportService.getReportSubscribers(type) >> users
        3 * incomeService.getIncomesFromDateInterval(_ as LocalDate, _ as LocalDate, _ as String) >> incomes
        3 * expenseService.getExpensesFromDateInterval(_ as LocalDate, _ as LocalDate, _ as String) >>> [expensesOfFirstUser, expensesOfSecondUser, expensesOfThirdUser]

        and:
        with(result) {
            result.size() == 2
            with(result.get(0)) {
                with(user()) {
                    id == "1"
                    username == "Anne"
                    email == "anne@gmail.com"
                    password == "anne123"
                    roles.size() == 1
                    roles.get(0) == Role.USER
                }
                with(dateInterval()) {
                    startDate() == LocalDate.of(2023, 9, 1)
                    endDate() == LocalDate.of(2023, 9, 4)
                }
                totalExpenses() == 99.22
                with(largestExpense()) {
                    date == LocalDate.of(2023, 9, 2)
                    price == 65.77
                }
                averageDailyExpense() == 24.81
                totalIncomes() == 1000.67
                budgetSummary() == 901.45
            }
            with(result.get(1)) {
                with(user()) {
                    id == "2"
                    username == "Johnny"
                    email == "johnny@gmail.com"
                    password == "johnny123"
                    roles.size() == 1
                    roles.get(0) == Role.USER
                }
                with(dateInterval()) {
                    startDate() == LocalDate.of(2023, 9, 1)
                    endDate() == LocalDate.of(2023, 9, 4)
                }
                totalExpenses() == 232.33
                with(largestExpense()) {
                    date == LocalDate.of(2023, 9, 2)
                    price == 166.56
                }
                averageDailyExpense() == 58.08
                totalIncomes() == 1000.67
                budgetSummary() == 768.34
            }
        }
    }

    private List<UserEntity> getUsers() {
        return List.of(
            new UserEntity("1", "Anne", "anne@gmail.com", "anne123", List.of(Role.USER)),
            new UserEntity("2", "Johnny", "johnny@gmail.com", "johnny123", List.of(Role.USER)),
            new UserEntity("3", "Matthew", "matthew@gmail.com", "matthew123", List.of(Role.USER))
        )
    }

    private List<IncomeEntity> getIncomes() {
        return List.of(
            getIncome(LocalDate.of(2023, 9, 1), 1000.67)
        )
    }

    private IncomeEntity getIncome(LocalDate date, BigDecimal amount) {
        return new IncomeEntity("1", date, amount, "description", null)
    }

    private List<ExpenseEntity> getExpensesOfFirstUser() {
        return List.of(
            getExpense(LocalDate.of(2023, 9, 1), 33.45),
            getExpense(LocalDate.of(2023, 9, 2), 65.77)
        )
    }

    private List<ExpenseEntity> getExpensesOfSecondUser() {
        return List.of(
            getExpense(LocalDate.of(2023, 9, 2), 166.56),
            getExpense(LocalDate.of(2023, 9, 14), 65.77)
        )
    }

    private ExpenseEntity getExpense(LocalDate date, BigDecimal price) {
        return new ExpenseEntity("1", date, null, price, null)
    }
}
