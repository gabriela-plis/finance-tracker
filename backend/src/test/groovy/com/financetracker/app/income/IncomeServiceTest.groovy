package com.financetracker.app.income


import com.financetracker.app.security.authorization.Role
import com.financetracker.app.user.User
import com.financetracker.app.user.UserService
import com.financetracker.app.utils.exception.DocumentNotFoundException
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import java.time.LocalDate

class IncomeServiceTest extends Specification{

    IncomeMapper incomeMapper = Mappers.getMapper(IncomeMapper)

    IncomeRepository incomeRepository = Mock()

    UserService userService = Mock()

    IncomeService incomeService = new IncomeService(incomeRepository, incomeMapper, userService)

    def"should get all user incomes"() {
        given:
        String userId = 1
        PageRequest pageable = PageRequest.of(0, 5)

        when:
        Page<Income> result = incomeService.getUserIncomes(userId, pageable)

        then:
        1 * incomeRepository.findByUser_Id(userId, pageable) >> getPagedIncomes()

        and:
        result == getPagedIncomes()
    }

    def"should get income by id"() {
        given:
        String incomeId = 1

        when:
        Income result = incomeService.getIncome(incomeId)

        then:
        1 * incomeRepository.findById(incomeId) >> Optional.of(getIncome())

        and:
        result == getIncome()
    }

    def"should throw DocumentNotFoundException when income was not found by id"() {
        given:
        String incomeId = 1

        when:
        incomeService.getIncome(incomeId)

        then:
        1 * incomeRepository.findById(incomeId) >> Optional.empty()

        and:
        thrown(DocumentNotFoundException)
    }

    def"should get user sorted incomes"() {
        given:
        String userId = 1
        PageRequest pageable = PageRequest.of(0 ,5)
        IncomeSortingCriteriaDTO sortingCriteria = new IncomeSortingCriteriaDTO(LocalDate.of(2020, 1, 1), LocalDate.of(2022, 1, 1), BigDecimal.valueOf(50), BigDecimal.valueOf(300), null)

        when:
        Page<Income> result = incomeService.getUserSortedIncomes(userId, sortingCriteria, pageable)

        then:
        1 * incomeRepository.findByUser_IdAndDateBetweenAndAmountBetweenAndDescriptionContainingIgnoreCase(userId, sortingCriteria.dateMin(), sortingCriteria.dateMax(), sortingCriteria.amountMin(), sortingCriteria.amountMax(), sortingCriteria.keyword(), pageable) >> getPagedIncomes()

        and:
        result == getPagedIncomes()
    }

    def"should create income"() {
        given:
        String userId = 1
        AddIncomeDTO incomeToAdd = new AddIncomeDTO(LocalDate.of(2020, 1, 1), BigDecimal.valueOf(100.50), "income")

        when:
        incomeService.createIncome(userId, incomeToAdd)

        then:
        1 * userService.getUserById(userId) >> getUser()
        1 * incomeRepository.insert(_ as Income)
    }

    def"should update income"() {
        given:
        String userId = 1
        IncomeDTO incomeToUpdate = getIncomeDTO()

        when:
        incomeService.updateIncome(userId, incomeToUpdate)

        then:
        1 * userService.getUserById(userId) >> getUser()
        1 * incomeRepository.save(_ as Income)
    }

    def"should delete income"() {
        given:
        String incomeId = 1

        when:
        incomeService.deleteIncome(incomeId)

        then:
        1 * incomeRepository.deleteById(incomeId)
    }

    private Page<Income> getPagedIncomes() {
        List<Income> incomes = List.of(
            getIncome("1"),
            getIncome("2")
        )

        return new PageImpl<Income>(incomes, PageRequest.of(0, 5), 5)
    }


    private Income getIncome() {
        return new Income("1", LocalDate.of(2020, 1, 1), BigDecimal.valueOf(100.50), "income", getUser())
    }

    private Income getIncome(String id) {
        return new Income(id, LocalDate.of(2020, 1, 1), BigDecimal.valueOf(100.50), "income", getUser())
    }

    private IncomeDTO getIncomeDTO() {
        return new IncomeDTO("1", LocalDate.of(2020, 1, 1), BigDecimal.valueOf(100.50), "income")
    }

    private User getUser() {
        return new User("1", "Anne", "Smith", "anne123", List.of(Role.USER))
    }
}
