package com.zaprogramujzycie.finanse.income;

import com.zaprogramujzycie.finanse.user.UserService;
import com.zaprogramujzycie.finanse.utils.converter.StringListToObjectIdListConverter;
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

    private final StringListToObjectIdListConverter converter;

    public PagedIncomeDTO getUserIncome(String userId, Pageable pageable) {
        return getPagedIncomesDTO(incomeRepository.findByUser_Id(userId, pageable));
    }

    public IncomeDTO getIncome(String incomeId) {
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow();

        return  incomeMapper.toDTO(income);
    }

    public PagedIncomeDTO getUserSortedIncome(String userId, IncomeSortingCriteriaDTO criteria, Pageable pageable) {
        Page<Income> income;
        if (criteria.categoryIds() == null) {
            income = incomeRepository.findByUserIdAndDateBetweenAndPriceBetween(userId, criteria.dateMin(), criteria.dateMax(), criteria.priceMin(), criteria.priceMax(), pageable);
        } else {
            income = incomeRepository.findByUserIdAndDateBetweenAndPriceBetweenAndCategoryIdIn(userId, criteria.dateMin(), criteria.dateMax(), criteria.priceMin(), criteria.priceMax(), converter.convert(criteria.categoryIds()), pageable);
        }

        return getPagedIncomesDTO(income);
    }

    private PagedIncomeDTO getPagedIncomesDTO(Page<Income> pagedIncomes) {
        List<IncomeDTO> incomes = incomeMapper.toDTOs(pagedIncomes.getContent());

        return new PagedIncomeDTO(pagedIncomes.getTotalPages(), pagedIncomes.getNumber(), incomes);
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
}
