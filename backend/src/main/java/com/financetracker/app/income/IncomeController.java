package com.financetracker.app.income;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;
    private final IncomeMapper incomeMapper;

    @GetMapping
    public PagedIncomesDTO getUserIncome(@PathVariable String userId, Pageable pageable) {
        return getPagedIncomesDTO(incomeService.getUserIncomes(userId, pageable));
    }

    @GetMapping("/{incomeId}")
    public IncomeDTO getIncome(@PathVariable String incomeId) {
        return incomeMapper.toDTO(incomeService.getIncome(incomeId));
    }

    @GetMapping("/criteria")
    public PagedIncomesDTO getUserIncomeByCriteria(@PathVariable String userId, @Valid IncomeSortingCriteriaDTO criteria, Pageable pageable) {
        return getPagedIncomesDTO(incomeService.getUserSortedIncomes(userId, criteria, pageable));
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

    private PagedIncomesDTO getPagedIncomesDTO(Page<Income> pagedIncomes) {
        List<IncomeDTO> incomes = incomeMapper.toDTOs(pagedIncomes.getContent());
        return new PagedIncomesDTO(pagedIncomes.getTotalPages(), pagedIncomes.getNumber(), incomes);
    }
}