package com.financetracker.app.report.generators;

import com.financetracker.api.mail.Template;
import com.financetracker.app.expense.ExpenseEntity;
import com.financetracker.app.expense.ExpenseService;
import com.financetracker.app.income.IncomeEntity;
import com.financetracker.app.income.IncomeService;
import com.financetracker.app.report.db.ReportTypeService;
import com.financetracker.app.report.types.*;
import com.financetracker.app.user.UserEntity;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.financetracker.app.expense.ExpenseOperationsPerformer.*;
import static com.financetracker.app.expense.ExpenseOperationsPerformer.getTotalExpenses;
import static com.financetracker.app.income.IncomeOperationsPerformer.getTotalIncomes;
import static com.financetracker.app.report.db.ReportType.*;

@RequiredArgsConstructor
public class GeneralWeeklyReportsGenerator implements WeeklyReportsGenerator<GeneralWeeklyReport> {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final ReportTypeService reportService;
    private final Clock clock;

    @Override
    public List<GeneralWeeklyReport> generate() {
        return getGeneralWeeklyReports();
    }

    @Override
    public Template getTemplate() {
        return Template.GENERAL_WEEKLY_REPORT;
    }

    private List<GeneralWeeklyReport> getGeneralWeeklyReports() {
        List<UserEntity> subscribers = reportService.getReportSubscribers(GENERAL_WEEKLY_REPORT);

        List<GeneralWeeklyReport> reports = new ArrayList<>();
        DateInterval dateInterval = getDateInterval();

        for(UserEntity subscriber : subscribers) {
            Optional<GeneralWeeklyReport> report = generateReport(subscriber, dateInterval);

            if (report.isEmpty()) {
                continue;
            }

            reports.add(report.get());
        }

        return reports;
    }

    private Optional<GeneralWeeklyReport> generateReport(UserEntity subscriber, DateInterval dateInterval) {
        List<IncomeEntity> incomes = incomeService.getIncomesFromDateInterval(dateInterval.startDate(), dateInterval.endDate(), subscriber.getId());
        List<ExpenseEntity> expenses = expenseService.getExpensesFromDateInterval(dateInterval.startDate(), dateInterval.endDate(), subscriber.getId());

        if (expenses.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(GeneralWeeklyReport.builder()
            .user(subscriber)
            .dateInterval(dateInterval)
            .totalExpenses(getTotalExpenses(expenses))
            .largestExpense(getLargestExpense(expenses))
            .averageDailyExpense(getAverageDailyExpense(expenses, Period.between(dateInterval.startDate(), dateInterval.endDate()).getDays() + 1 ))
            .totalIncomes(getTotalIncomes(incomes))
            .budgetSummary(getTotalIncomes(incomes).subtract(getTotalExpenses(expenses)))
            .build());
    }

    private DateInterval getDateInterval() {
        LocalDate startDate = LocalDate.now(clock).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).minusWeeks(1);
        LocalDate endDate = LocalDate.now(clock).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        if (endDate.getMonth() != startDate.getMonth()) {
            startDate = endDate.withDayOfMonth(1);
        }

        return new DateInterval(startDate, endDate);
    }
}
