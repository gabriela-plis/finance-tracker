package com.zaprogramujzycie.finanse.expense;

import com.zaprogramujzycie.finanse.user.UserService;
import com.zaprogramujzycie.finanse.utils.converter.StringListToObjectIdListConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    private final UserService userService;

    private final StringListToObjectIdListConverter converter;

    public PagedExpensesDTO getUserExpenses(String userId, Pageable pageable) {
        return getPagedExpensesDTO(expenseRepository.findByUser_Id(userId, pageable));
    }

    public ExpenseDTO getExpense(String expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow();

        return  expenseMapper.toDTO(expense);
    }

    public PagedExpensesDTO getUserSortedExpenses(String userId, ExpenseSortingCriteriaDTO criteria, Pageable pageable) {
        Page<Expense> expenses;
        if (criteria.categoryIds() == null) {
            expenses = expenseRepository.findByUserIdAndDateBetweenAndPriceBetween(userId, criteria.dateMin(), criteria.dateMax(), criteria.priceMin(), criteria.priceMax(), pageable);
        } else {
            expenses = expenseRepository.findByUserIdAndDateBetweenAndPriceBetweenAndCategoryIdIn(userId, criteria.dateMin(), criteria.dateMax(), criteria.priceMin(), criteria.priceMax(), converter.convert(criteria.categoryIds()), pageable);
        }

        return getPagedExpensesDTO(expenses);
    }

    private PagedExpensesDTO getPagedExpensesDTO(Page<Expense> pagedExpenses) {
        List<ExpenseDTO> expenses = expenseMapper.toDTOs(pagedExpenses.getContent());

        return new PagedExpensesDTO(pagedExpenses.getTotalPages(), pagedExpenses.getNumber(), expenses);
    }


    public void createExpense(String userId, AddExpenseDTO expenseToAdd) {
            Expense expense = expenseMapper.toEntity(expenseToAdd);
            expense.setUser(userService.getUserById(userId));

            expenseRepository.insert(expense);
    }

    public void updateExpense(String userId, ExpenseDTO expenseToUpdate) {
            Expense expense = expenseMapper.toEntity(expenseToUpdate);
            expense.setUser(userService.getUserById(userId));

            expenseRepository.save(expense);
    }

    public void deleteExpense(String expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}
