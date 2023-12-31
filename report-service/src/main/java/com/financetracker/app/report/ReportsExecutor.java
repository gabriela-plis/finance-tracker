package com.financetracker.app.report;

import com.financetracker.api.mail.MailDTO;
import com.financetracker.api.mail.Template;
import com.financetracker.app.mail.MailService;
import com.financetracker.app.report.generators.MonthlyReportsGenerator;
import com.financetracker.app.report.generators.ReportsGenerator;
import com.financetracker.app.report.generators.WeeklyReportsGenerator;
import com.financetracker.app.report.types.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReportsExecutor {

    private final List<ReportsGenerator<?>> generators;
    private final MailService mailService;

    @Scheduled(cron = "0 0 8 1 1/1 *")
    public void executeMonthlyReports() {
        executeReports(MonthlyReportsGenerator.class);
    }

    @Scheduled(cron = "0 0 8 * * MON")
    public void executeWeeklyReports() {
        executeReports(WeeklyReportsGenerator.class);
    }

    private <T extends ReportsGenerator<?>> void executeReports(Class<T> clazz) {
        List<ReportsGenerator<?>> generators = getGenerators(clazz);

        Map<Template, List<? extends Report>> reports = generateReports(generators);
        List<MailDTO> mails = createMails(reports);

        if (mails.isEmpty()) return;

        sendMails(mails);
    }

    private Map<Template, List<? extends Report>> generateReports(List<ReportsGenerator<?>> generators) {
        return generators.stream()
            .collect(Collectors.toMap(
                ReportsGenerator::getTemplate,
                ReportsGenerator::generate
            ));
    }

    private List<MailDTO> createMails(Map<Template, List<? extends Report>> reports) {
        return reports.entrySet().stream()
            .flatMap(entry -> entry.getValue().stream()
                .map(report -> mailService.createMail(report, entry.getKey(), entry.getKey().getTitle()))
            )
            .toList();
    }

    private void sendMails(List<MailDTO> mails) {
        mails.forEach(mailService::sendMail);
    }

    @SuppressWarnings("unchecked")
    private <T extends ReportsGenerator<?>> List<ReportsGenerator<?>> getGenerators(Class<T> clazz) {
        return (List<ReportsGenerator<?>>) generators.stream()
            .filter(clazz::isInstance)
            .map(clazz::cast)
            .toList();
    }
}
