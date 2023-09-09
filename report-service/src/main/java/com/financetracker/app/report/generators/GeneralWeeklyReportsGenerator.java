package com.financetracker.app.report.generators;

import com.financetracker.api.mail.Template;
import com.financetracker.app.expense.ExpenseEntity;
import com.financetracker.app.expense.ExpenseService;
import com.financetracker.app.income.IncomeEntity;
import com.financetracker.app.income.IncomeService;
import com.financetracker.app.report.types.*;
import com.financetracker.app.user.UserEntity;
import com.financetracker.app.user.UserService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.financetracker.app.expense.ExpenseOperationsPerformer.*;
import static com.financetracker.app.expense.ExpenseOperationsPerformer.getTotalExpenses;
import static com.financetracker.app.income.IncomeOperationsPerformer.getTotalIncomes;
import static com.financetracker.app.report.db.ReportType.*;

@RequiredArgsConstructor
public class GeneralWeeklyReportsGenerator implements WeeklyReportsGenerator<GeneralWeeklyReport> {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final UserService userService;

    @Override
    public List<GeneralWeeklyReport> generate() {
        return getGeneralWeeklyReports();
    }

    @Override
    public Template getTemplate() {
        return Template.GENERAL_WEEKLY_REPORT;
    }

    private List<GeneralWeeklyReport> getGeneralWeeklyReports() {
        List<UserEntity> subscribers = userService.getReportSubscribers(GENERAL_WEEKLY_REPORT);

        List<GeneralWeeklyReport> reports = new ArrayList<>();
        DateInterval dateInterval = new DateInterval(LocalDate.now().minusWeeks(1).withDayOfMonth(1), LocalDate.now().withDayOfMonth(1).minusDays(1));

        for(UserEntity subscriber : subscribers) {
            List<IncomeEntity> incomes = incomeService.getIncomesFromDateInterval(dateInterval.startDate(), dateInterval.endDate(), subscriber.getId());
            List<ExpenseEntity> expenses = expenseService.getExpensesFromDateInterval(dateInterval.startDate(), dateInterval.endDate(), subscriber.getId());

            GeneralWeeklyReport report = GeneralWeeklyReport.builder()
                .user(subscriber)
                .dateInterval(dateInterval)
                .totalExpenses(getTotalExpenses(expenses))
                .largestExpense(getLargestExpense(expenses))
                .averageDailyExpense(getAverageDailyExpense(expenses))
                .totalIncomes(getTotalIncomes(incomes))
                .budgetSummary(getTotalIncomes(incomes).subtract(getTotalExpenses(expenses)))
                .build();

            reports.add(report);
        }

        return reports;
    }
}
