package com.financetracker.app.report.generators;

import com.financetracker.api.mail.Template;
import com.financetracker.app.expense.ExpenseEntity;
import com.financetracker.app.expense.ExpenseService;
import com.financetracker.app.income.IncomeEntity;
import com.financetracker.app.income.IncomeService;
import com.financetracker.app.report.db.ReportTypeService;
import com.financetracker.app.report.types.DateInterval;
import com.financetracker.app.report.types.GeneralMonthlyReport;
import com.financetracker.app.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.financetracker.app.expense.ExpenseOperationsPerformer.*;
import static com.financetracker.app.income.IncomeOperationsPerformer.getTotalIncomes;
import static com.financetracker.app.report.db.ReportType.GENERAL_MONTHLY_REPORT;

@Component
@RequiredArgsConstructor
public class GeneralMonthlyReportsGenerator implements MonthlyReportsGenerator<GeneralMonthlyReport> {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final ReportTypeService reportService;
    private final Clock clock;

    @Override
    public List<GeneralMonthlyReport> generate() {
        return generateGeneralMonthlyReports();
    }

    public Template getTemplate() {
        return Template.GENERAL_MONTHLY_REPORT;
    }

    private List<GeneralMonthlyReport> generateGeneralMonthlyReports() {
        List<UserEntity> subscribers = reportService.getReportSubscribers(GENERAL_MONTHLY_REPORT);

        List<GeneralMonthlyReport> reports = new ArrayList<>();
        DateInterval dateInterval = getDateInterval();

        for(UserEntity subscriber : subscribers) {
            Optional<GeneralMonthlyReport> report = generateReport(subscriber, dateInterval);

            if (report.isEmpty()) {
                continue;
            }

            reports.add(report.get());
        }

        return reports;
    }

    private Optional<GeneralMonthlyReport> generateReport(UserEntity subscriber, DateInterval dateInterval) {
        List<IncomeEntity> incomes = incomeService.getIncomesFromDateInterval(dateInterval.startDate(), dateInterval.endDate(), subscriber.getId());
        List<ExpenseEntity> expenses = expenseService.getExpensesFromDateInterval(dateInterval.startDate(), dateInterval.endDate(), subscriber.getId());

        if (expenses.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(GeneralMonthlyReport.builder()
            .user(subscriber)
            .dateInterval(dateInterval)
            .totalExpenses(getTotalExpenses(expenses))
            .largestExpense(getLargestExpense(expenses))
            .averageWeeklyExpense(getAverageWeeklyExpense(expenses))
            .weekWithHighestExpenses(getWeekWithHighestExpenses(expenses))
            .dayWithHighestAverageExpense(getDayWithHighestAverageExpense(expenses))
            .totalIncomes(getTotalIncomes(incomes))
            .budgetSummary(getTotalIncomes(incomes).subtract(getTotalExpenses(expenses)))
            .build());
    }

    private DateInterval getDateInterval() {
        LocalDate startDate = LocalDate.now(clock).minusMonths(1).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now(clock).withDayOfMonth(1).minusDays(1);

        return new DateInterval(startDate, endDate);
    }
}