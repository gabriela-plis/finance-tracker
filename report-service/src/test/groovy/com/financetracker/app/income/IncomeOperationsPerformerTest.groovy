package com.financetracker.app.income

import spock.lang.Specification

import java.time.LocalDate

import static com.financetracker.app.income.IncomeOperationsPerformer.*

class IncomeOperationsPerformerTest extends Specification {

    def "should get total incomes"() {
        given:
        List<IncomeEntity> incomes = getIncomes()

        when:
        BigDecimal result = getTotalIncomes(incomes)

        then:
        result == 1200.92
    }

    private List<IncomeEntity> getIncomes() {
        return List.of(
            getIncome(LocalDate.of(2023, 9, 1), 1000.67),
            getIncome(LocalDate.of(2023, 9, 10), 200.25)
        )
    }

    private IncomeEntity getIncome(LocalDate date, BigDecimal amount) {
        return new IncomeEntity("1", date, amount, "description", null)
    }
}
