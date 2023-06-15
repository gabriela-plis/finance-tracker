package com.zaprogramujzycie.finanse.expense;

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

    public PagedExpensesDTO getExpenses(String userId, Pageable pageable) {
        return getPagedExpensesDTO(expenseRepository.findByUserId(userId, pageable));
    }

    public ExpenseDTO getExpense(String expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow();

        return  expenseMapper.toDTO(expense);
    }

    public PagedExpensesDTO getSortedExpenses(String userId, ExpenseSortingCriteriaDTO criteria, Pageable pageable) {
        return getPagedExpensesDTO(expenseRepository.findByUserIdAndDateBetweenAndPriceBetweenAndCategoryAndOrderByDateDesc(userId, criteria.dateMin(), criteria.dateMax(), criteria.priceMin(), criteria.priceMax(), criteria.category(), pageable));
    }

    private PagedExpensesDTO getPagedExpensesDTO(Page<Expense> pagedExpenses) {
        List<ExpenseDTO> expenses = expenseMapper.toDTOs(pagedExpenses.getContent());

        return new PagedExpensesDTO(pagedExpenses.getTotalPages(), pagedExpenses.getNumber(), expenses);
    }


    public void createExpense(String userId, AddExpenseDTO expenseToAdd) {
        //with userId addExpenseDTO?
        Expense expense = expenseMapper.toEntity(expenseToAdd);
        expense.setUserId(userId);

        expenseRepository.insert(expense);
    }

    public void updateExpense(ExpenseDTO expense) {
        expenseRepository.save(expenseMapper.toEntity(expense));
    }

    public void deleteExpense(String expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}
