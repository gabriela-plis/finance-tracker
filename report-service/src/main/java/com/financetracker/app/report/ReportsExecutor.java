package com.financetracker.app.report;

import com.financetracker.app.mail.MailDTO;
import com.financetracker.app.mail.MailService;
import com.financetracker.app.mail.Template;
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
        List<ReportsGenerator<?>> generators = getGenerators(MonthlyReportsGenerator.class);

        Map<Template, List<? extends Report>> reports = generateReports(generators);
        List<MailDTO> mails = createMails(reports);
        sendMails(mails);
    }

    @Scheduled(cron = "0 0 8 * * MON")
    public void executeWeeklyReports() {
        List<ReportsGenerator<?>> generators = getGenerators(WeeklyReportsGenerator.class);

        Map<Template, List<? extends Report>> reports = generateReports(generators);
        List<MailDTO> mails = createMails(reports);
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
