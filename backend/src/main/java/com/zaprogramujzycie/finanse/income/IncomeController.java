package com.zaprogramujzycie.finanse.income;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping
    public PagedIncomesDTO getUserIncome(@PathVariable String userId, Pageable pageable) {
        return incomeService.getUserIncomes(userId, pageable);
    }

    @GetMapping("/{incomeId}")
    public IncomeDTO getIncome(@PathVariable String incomeId) {
        return incomeService.getIncome(incomeId);
    }

    @GetMapping("/criteria")
    public PagedIncomesDTO getUserIncomeByCriteria(@PathVariable String userId, @Valid IncomeSortingCriteriaDTO criteria, Pageable pageable) {
        return incomeService.getUserSortedIncomes(userId, criteria, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createIncome(@PathVariable String userId, @RequestBody @Valid AddIncomeDTO income){
        incomeService.createIncome(userId, income);
    }

    @PutMapping("/{incomeId}")
    public void updateIncome(@PathVariable String userId, @RequestBody @Valid IncomeDTO income){
        incomeService.updateIncome(userId, income);
    }

    @DeleteMapping("/{incomeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIncome(@PathVariable String incomeId){
        incomeService.deleteIncome(incomeId);
    }
}