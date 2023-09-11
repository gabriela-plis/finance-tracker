package com.financetracker.app.income

import spock.lang.Specification

import java.time.LocalDate

class IncomeServiceTest extends Specification {

    IncomeRepository incomeRepository = Mock()

    IncomeService incomeService = new IncomeService(incomeRepository)

    def "should return incomes from date interval"() {
        given:
        def startDate = LocalDate.of(2023, 1, 1)
        def endDate = LocalDate.of(2023, 1, 2)
        String userId = 1

        List<IncomeEntity> incomes = List.of(
            new IncomeEntity("1", LocalDate.of(2023, 1, 1), 100.50, "description", null)
        )

        when:
        List<IncomeEntity> result =  incomeService.getIncomesFromDateInterval(startDate, endDate, userId)

        then:
        1 * incomeRepository.findByDateBetweenAndUserId(startDate, endDate, userId) >> incomes

        and:
        with(result[0]) {
            id == "1"
            date == LocalDate.of(2023, 1, 1)
            amount == 100.50
            description == "description"
        }
    }

}
