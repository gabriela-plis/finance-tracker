package com.financetracker.app.expense;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;

    public List<ExpenseEntity> getLastMonthExpenses(String userId) {
        return null;
    }

    public List<ExpenseEntity> getLastWeekExpenses(String userId) {
        return null;
    }
}
