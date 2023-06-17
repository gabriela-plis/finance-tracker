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
    public PagedExpensesDTO getUserExpenses(@PathVariable String userId, Pageable pageable) {
        return expenseService.getUserExpenses(userId, pageable);
    }

    @GetMapping("/{expenseId}")
    public ExpenseDTO getExpense(@PathVariable String expenseId) {
        return expenseService.getExpense(expenseId);
    }

    @GetMapping("/criteria")
    public PagedExpensesDTO getUserExpensesByCriteria(@PathVariable String userId, @Valid ExpenseSortingCriteriaDTO criteria, Pageable pageable) {
        return expenseService.getUserSortedExpenses(userId, criteria, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createExpense(@PathVariable String userId, @RequestBody @Valid AddExpenseDTO expense){
        expenseService.createExpense(userId, expense);
    }

    @PutMapping("/{expenseId}")
    public void updateExpense(@PathVariable String userId, @RequestBody @Valid ExpenseDTO expense){
        expenseService.updateExpense(userId, expense);
    }

    @DeleteMapping("/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable String expenseId){
        expenseService.deleteExpense(expenseId);
    }

}
