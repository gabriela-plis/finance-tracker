package com.financetracker.app.expense;

import com.financetracker.app.report.types.DateRange;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ExpenseOperationsPerformer {

    //TODO think about method, field names (expense vs expensePrice)
    
    public static BigDecimal getTotalExpenses(List<ExpenseEntity> expenses) {
        return expenses.stream()
            .map(ExpenseEntity::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static ExpenseEntity getLargestExpense(List<ExpenseEntity> expenses) {
        return expenses.stream()
            .max((expense1, expense2) -> expense1.getPrice().compareTo(expense2.getPrice()))
            .get();
    }

    public static BigDecimal getAverageDailyExpense(List<ExpenseEntity> expenses) {
        return expenses.stream()
            .map(ExpenseEntity::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(LocalDate.now().getMonth().length(false))); //TODO determine leap year
    }

    public static BigDecimal getAverageWeeklyExpense(List<ExpenseEntity> expenses) {
        return expenses.stream()
            .map(ExpenseEntity::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(4));
    }

    public static DateRange getWeekWithHighestExpenses(List<ExpenseEntity> expenses) {
        Map<LocalDate, BigDecimal> weeklyTotalExpenses = expenses.stream()
            .collect(Collectors.groupingBy(
                expense -> expense.getDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
                Collectors.mapping(ExpenseEntity::getPrice, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
            ));

        LocalDate startOfWeek = weeklyTotalExpenses.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElseThrow()
            .getKey();

        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return new DateRange(startOfWeek, endOfWeek);
    }

    public static DayOfWeek getDayWithHighestAverageExpense(List<ExpenseEntity> expenses) {
        Map<DayOfWeek, BigDecimal> averageExpensesByDay = expenses.stream()
            .collect(Collectors.groupingBy(
                expense -> expense.getDate().getDayOfWeek(),
                Collectors.mapping(ExpenseEntity::getPrice, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
            ))
            .entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().divide(BigDecimal.valueOf(entry.getKey().getValue()))
            ));

        return averageExpensesByDay.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElseThrow()
            .getKey();
    }
}
