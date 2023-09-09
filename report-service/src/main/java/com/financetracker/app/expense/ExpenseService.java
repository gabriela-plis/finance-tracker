package com.financetracker.app.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public List<ExpenseEntity> getExpensesFromDateInterval(LocalDate startDate, LocalDate endDate, String userId) {
        return expenseRepository.findByDateBetweenAndUserId(startDate, endDate, userId);
    }
}
