package com.financetracker.app.expense;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;

    @GetMapping
    public PagedExpensesDTO getUserExpenses(@PathVariable String userId, Pageable pageable) {
        return getPagedExpensesDTO(expenseService.getUserExpenses(userId, pageable));
    }

    @GetMapping("/{expenseId}")
    public ExpenseDTO getExpense(@PathVariable String expenseId) {
        return expenseMapper.toDTO(expenseService.getExpense(expenseId));
    }

    @GetMapping("/criteria")
    public PagedExpensesDTO getUserExpensesByCriteria(@PathVariable String userId, @Valid ExpenseSortingCriteriaDTO criteria, Pageable pageable) {
        return getPagedExpensesDTO(expenseService.getUserSortedExpenses(userId, criteria, pageable));
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

    private PagedExpensesDTO getPagedExpensesDTO(Page<Expense> pagedExpenses) {
        List<ExpenseDTO> expenses = expenseMapper.toDTOs(pagedExpenses.getContent());

        return new PagedExpensesDTO(pagedExpenses.getTotalPages(), pagedExpenses.getNumber(), expenses);
    }

}
