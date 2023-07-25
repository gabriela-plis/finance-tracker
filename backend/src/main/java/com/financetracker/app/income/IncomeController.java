package com.financetracker.app.income;

import com.financetracker.app.security.authentication.AuthenticationService;
import com.financetracker.app.utils.exception.custom.IdNotMatchException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users/me/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;
    private final IncomeMapper incomeMapper;
    private final AuthenticationService authenticationService;

    @GetMapping
    public PagedIncomesDTO getUserIncome(Pageable pageable, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        return getPagedIncomesDTO(incomeService.getUserIncomes(userId, pageable));
    }

    @GetMapping("/{incomeId}")
    public IncomeDTO getIncome(@PathVariable String incomeId) {
        return incomeMapper.toDTO(incomeService.getIncome(incomeId));
    }

    @GetMapping("/criteria")
    public PagedIncomesDTO getUserIncomeByCriteria(@Valid IncomeSortingCriteriaDTO criteria, Pageable pageable, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        return getPagedIncomesDTO(incomeService.getUserSortedIncomes(userId, criteria, pageable));
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
    public void deleteIncome(@PathVariable String incomeId){
        incomeService.deleteIncome(incomeId);
    }

    private PagedIncomesDTO getPagedIncomesDTO(Page<Income> pagedIncomes) {
        List<IncomeDTO> incomes = incomeMapper.toDTOs(pagedIncomes.getContent());
        return new PagedIncomesDTO(pagedIncomes.getTotalPages(), pagedIncomes.getNumber(), incomes);
    }
}