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
        ReportsGenerator generator = getGenerator(MonthlyReportsGenerator.class);
        List<Report> reports = generator.generate();
        sendReports(reports);
    }

    @Scheduled(cron = "0 0 8 * * MON")
    public void executeWeeklyReports() {
        ReportsGenerator generator = getGenerator(WeeklyReportsGenerator.class);
        List<Report> reports = generator.generate();
        sendReports(reports);
    }

    private void sendReports(List<Report> reports) {
        for(Report report : reports) {
            queueSender.send(report);
        }
    }

    private <T extends ReportsGenerator> T getGenerator(Class<T> clazz) {
        return generators.stream().filter(clazz::isInstance)
            .map(clazz::cast)  //TODO wonder why it needs to be casting to T
            .findFirst()
            .orElseThrow(IllegalStateException::new); //TODO what type of exception should be throw
    }
}
