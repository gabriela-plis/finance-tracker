package com.financetracker.app.expense;

import com.financetracker.app.security.authentication.AuthenticationService;
import com.financetracker.app.utils.exception.custom.IdNotMatchException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;
    private final AuthenticationService authenticationService;

    @GetMapping
    public PagedExpensesDTO getUserExpenses(Pageable pageable, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        return expenseMapper.toPagedDTO(expenseService.getUserExpenses(userId, pageable));
    }

    @GetMapping("/{expenseId}")
    public ExpenseDTO getExpense(@PathVariable String expenseId, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        return expenseMapper.toDTO(expenseService.getExpense(expenseId, userId));
    }

    @GetMapping("/criteria")
    public PagedExpensesDTO getUserExpensesByCriteria(@Valid ExpenseSortingCriteriaDTO criteria, Pageable pageable, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        return expenseMapper.toPagedDTO(expenseService.getUserSortedExpenses(userId, criteria, pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createExpense(@RequestBody @Valid AddExpenseDTO expense, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        expenseService.createExpense(userId, expense);
    }

    @PutMapping("/{expenseId}")
    public void updateExpense(@PathVariable String expenseId, @RequestBody @Valid ExpenseDTO expense, Authentication authentication) {
        if (!expense.id().equals(expenseId)) {
            throw new IdNotMatchException();
        }
        String userId = authenticationService.getUserId(authentication);
        expenseService.updateExpense(userId, expense);
    }

    @DeleteMapping("/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable String expenseId, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        expenseService.deleteExpense(expenseId, userId);
    }

}
