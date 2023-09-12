package com.financetracker.app.expense;

import com.financetracker.app.report.types.DateInterval;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ExpenseOperationsPerformer {

    public static BigDecimal getTotalExpenses(List<ExpenseEntity> expenses) {
        return expenses.stream()
            .map(ExpenseEntity::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static ExpenseEntity getLargestExpense(List<ExpenseEntity> expenses) {
        return expenses.stream()
            .max(Comparator.comparing(ExpenseEntity::getPrice))
            .orElse(null);
    }

    public static BigDecimal getAverageDailyExpense(List<ExpenseEntity> expenses, Month month) {
        return expenses.stream()
            .map(ExpenseEntity::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(month.length(Year.now().isLeap())), RoundingMode.HALF_UP);
    }

    public static BigDecimal getAverageWeeklyExpense(List<ExpenseEntity> expenses) {
        return expenses.stream()
            .map(ExpenseEntity::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(4), RoundingMode.HALF_UP);
    }

    public static DateInterval getWeekWithHighestExpenses(List<ExpenseEntity> expenses) {
        if (expenses.isEmpty()) {
            return null;
        }

        Map<LocalDate, BigDecimal> weeklyTotalExpenses = expenses.stream()
            .collect(Collectors.groupingBy(
                expense -> expense.getDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
                Collectors.mapping(ExpenseEntity::getPrice, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
            ));

        LocalDate startOfWeek = weeklyTotalExpenses.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(IllegalArgumentException::new);

        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return new DateInterval(startOfWeek, endOfWeek);
    }

    public static DayOfWeek getDayWithHighestAverageExpense(List<ExpenseEntity> expenses) {
        if (expenses.isEmpty()) {
            return null;
        }

        Map<DayOfWeek, List<BigDecimal>> expensesPriceByDay = expenses.stream()
            .collect(Collectors.groupingBy(
                expense -> expense.getDate().getDayOfWeek(),
                Collectors.mapping(ExpenseEntity::getPrice, Collectors.toList())
            ));

        return expensesPriceByDay.entrySet().stream()
            .max(Comparator.comparingDouble(entry ->
                entry.getValue().stream()
                    .mapToDouble(BigDecimal::doubleValue)
                    .average()
                    .orElse(0)
            ))
            .map(Map.Entry::getKey)
            .orElse(null);
    }
}
