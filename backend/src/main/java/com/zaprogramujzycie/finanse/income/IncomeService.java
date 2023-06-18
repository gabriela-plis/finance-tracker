package com.zaprogramujzycie.finanse.income;

import com.zaprogramujzycie.finanse.user.UserService;
import com.zaprogramujzycie.finanse.utils.exception.DocumentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;

    private final UserService userService;

    public PagedIncomesDTO getUserIncomes(String userId, Pageable pageable) {
        return getPagedIncomesDTO(incomeRepository.findByUser_Id(userId, pageable));
    }

    public IncomeDTO getIncome(String incomeId) {
        Income income = incomeRepository.findById(incomeId)
            .orElseThrow(DocumentNotFoundException::new);

        return incomeMapper.toDTO(income);
    }

    public PagedIncomesDTO getUserSortedIncomes(String userId, IncomeSortingCriteriaDTO criteria, Pageable pageable) {
        Page<Income> incomes;
        incomes = incomeRepository.findByUser_IdAndDateBetweenAndAmountBetweenAndDescriptionContainingIgnoreCase(userId, criteria.dateMin(), criteria.dateMax(), criteria.amountMin(), criteria.amountMax(), criteria.keyword(), pageable);

        return getPagedIncomesDTO(incomes);
    }

    public void createIncome(String userId, AddIncomeDTO incomeToAdd) {
        Income income = incomeMapper.toEntity(incomeToAdd);
        income.setUser(userService.getUserById(userId));

        incomeRepository.insert(income);
    }

    public void updateIncome(String userId, IncomeDTO incomeToUpdate) {
        Income income = incomeMapper.toEntity(incomeToUpdate);
        income.setUser(userService.getUserById(userId));

        incomeRepository.save(income);
    }

    public void deleteIncome(String incomeId) {
        incomeRepository.deleteById(incomeId);
    }

    private PagedIncomesDTO getPagedIncomesDTO(Page<Income> pagedIncomes) {
        List<IncomeDTO> incomes = incomeMapper.toDTOs(pagedIncomes.getContent());

        return new PagedIncomesDTO(pagedIncomes.getTotalPages(), pagedIncomes.getNumber(), incomes);
    }
}
