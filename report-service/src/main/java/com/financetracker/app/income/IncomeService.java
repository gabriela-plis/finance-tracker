package com.financetracker.app.income;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository IncomeRepository;

    public List<IncomeEntity> getIncomesFromDateInterval(LocalDate startDate, LocalDate endDate, String userId) {
        return IncomeRepository.findByDateBetweenAndUserId(startDate, endDate, userId);
    }
}
