package com.financetracker.app.income;

import com.financetracker.app.security.authentication.AuthenticationService;
import com.financetracker.app.utils.exception.custom.IdNotMatchException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;
    private final IncomeMapper incomeMapper;
    private final AuthenticationService authenticationService;

    @GetMapping
    public PagedIncomesDTO getUserIncomes(Pageable pageable, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        return incomeMapper.toPagedDTO(incomeService.getUserIncomes(userId, pageable));
    }

    @GetMapping("/{incomeId}")
    public IncomeDTO getIncome(@PathVariable String incomeId, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        return incomeMapper.toDTO(incomeService.getIncome(incomeId, userId));
    }

    @GetMapping("/criteria")
    public PagedIncomesDTO getUserIncomesByCriteria(@Valid IncomeSortingCriteriaDTO criteria, Pageable pageable, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        return incomeMapper.toPagedDTO(incomeService.getUserSortedIncomes(userId, criteria, pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createIncome(@RequestBody @Valid AddIncomeDTO income, Authentication authentication){
        String userId = authenticationService.getUserId(authentication);
        incomeService.createIncome(userId, income);
    }

    @PutMapping("/{incomeId}")
    public void updateIncome(@PathVariable String incomeId, @RequestBody @Valid IncomeDTO income, Authentication authentication){
        if (!income.id().equals(incomeId)) {
            throw new IdNotMatchException();
        }

        String userId = authenticationService.getUserId(authentication);
        incomeService.updateIncome(userId, income);
    }

    @DeleteMapping("/{incomeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIncome(@PathVariable String incomeId, Authentication authentication){
        String userId = authenticationService.getUserId(authentication);
        incomeService.deleteIncome(incomeId, userId);
    }

}