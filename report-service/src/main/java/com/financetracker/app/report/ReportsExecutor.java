package com.financetracker.app.report;

import com.financetracker.app.rabbitmq.QueueSender;
import com.financetracker.app.report.generators.MonthlyReportsGenerator;
import com.financetracker.app.report.generators.ReportsGenerator;
import com.financetracker.app.report.generators.WeeklyReportsGenerator;
import com.financetracker.app.report.types.Report;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Set;

public class ReportsExecutor {
    Set<ReportsGenerator> generators;
    QueueSender queueSender;

    @Scheduled(cron = "0 0 8 1 1/1 *")
    public void executeMonthlyReports() {
        //find generators for monthly reports
        ReportsGenerator generator = generators.stream().filter(g -> g instanceof MonthlyReportsGenerator)
            .findFirst()
            .orElseThrow();

        //generate reports (which layer should connect with database to retrieve appropriate incomes, expenses - executor or specific generator?)
        List<Report> reports = generator.generate();

        //send it
        sendReports(reports);
    }

    @Scheduled(cron = "0 0 8 * * MON")
    public void executeWeeklyReports() {
        ReportsGenerator generator = generators.stream().filter(g -> g instanceof WeeklyReportsGenerator)
            .findFirst()
            .orElseThrow();

        List<Report> reports = generator.generate();
        sendReports(reports);
    }

    private void sendReports(List<Report> reports) {
        for(Report report : reports) {
            queueSender.send(report);
        }
    }
}
