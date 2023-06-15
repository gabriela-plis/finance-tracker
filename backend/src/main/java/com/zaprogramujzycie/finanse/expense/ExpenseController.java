package com.zaprogramujzycie.finanse.expense;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public PagedExpensesDTO getUserExpenses(@RequestParam String userId, Pageable pageable) {
        return expenseService.getExpenses(userId, pageable);
    }

    @GetMapping("/{expenseId}")
    public ExpenseDTO getExpense(@RequestParam String expenseId) {
        return expenseService.getExpense(expenseId);
    }

    @GetMapping("/criteria")
    public PagedExpensesDTO getUserExpensesByCriteria(@RequestParam String userId, @Valid ExpenseSortingCriteriaDTO criteria, Pageable pageable) {
        return expenseService.getSortedExpenses(userId, criteria, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createExpense(String userId, @Valid AddExpenseDTO expense){
        expenseService.createExpense(userId, expense);
    }

    @PutMapping
    public void updateExpense(@Valid ExpenseDTO expense){
        expenseService.updateExpense(expense);
    }

    @DeleteMapping("/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(String expenseId){
        expenseService.deleteExpense(expenseId);
    }

}
