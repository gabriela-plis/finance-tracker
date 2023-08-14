package com.financetracker.app.report.generators;

import com.financetracker.app.expense.ExpenseEntity;
import com.financetracker.app.expense.ExpenseService;
import com.financetracker.app.income.IncomeEntity;
import com.financetracker.app.income.IncomeService;
import com.financetracker.app.mail.Template;
import com.financetracker.app.report.types.DateRange;
import com.financetracker.app.report.types.GeneralMonthlyReport;
import com.financetracker.app.user.UserEntity;
import com.financetracker.app.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.financetracker.app.expense.ExpenseOperationsPerformer.*;
import static com.financetracker.app.income.IncomeOperationsPerformer.getTotalIncomes;
import static com.financetracker.app.report.db.ReportType.*;

@Component
@RequiredArgsConstructor
public class GeneralMonthlyReportsGenerator implements MonthlyReportsGenerator<GeneralMonthlyReport> {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final UserService userService;

    @Override
    public List<GeneralMonthlyReport> generate() {
        return generateGeneralMonthlyReports();
    }

    public Template getTemplate() {
        return Template.GENERAL_MONTHLY_REPORT;
    }

    private List<GeneralMonthlyReport> generateGeneralMonthlyReports() {
        List<UserEntity> subscribers = userService.getReportSubscribers(GENERAL_MONTHLY_REPORT);

        List<GeneralMonthlyReport> reports = new ArrayList<>();

        for(UserEntity subscriber : subscribers) {
            List<IncomeEntity> incomes = incomeService.getLastMonthIncomes("");
            List<ExpenseEntity> expenses = expenseService.getLastMonthExpenses("");

            GeneralMonthlyReport report = GeneralMonthlyReport.builder()
                .user(subscriber)
                .dateRange(new DateRange(LocalDate.now().withDayOfMonth(1), LocalDate.now().minusMonths(1).withDayOfMonth(1)))
                .totalExpenses(getTotalExpenses(expenses))
                .largestExpense(getLargestExpense(expenses))
                .averageWeeklyExpense(getAverageWeeklyExpense(expenses))
                .weekWithHighestExpenses(getWeekWithHighestExpenses(expenses))
                .dayWithHighestAverageExpense(getDayWithHighestAverageExpense(expenses))
                .totalIncomes(getTotalIncomes(incomes))
                .budgetSummary(getTotalIncomes(incomes).subtract(getTotalExpenses(expenses)))
                .build();

            reports.add(report);
        }

        return reports;
    }
}