package com.financetracker.app.income;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {
    private IncomeRepository IncomeRepository;

    public List<IncomeEntity> getLastMonthIncomes(String userId) {
        return null;
    }

    public List<IncomeEntity> getLastWeekIncomes(String userId) {
        return null;
    }
}
