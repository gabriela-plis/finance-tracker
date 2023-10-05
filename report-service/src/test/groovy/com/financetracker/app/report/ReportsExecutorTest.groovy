package com.financetracker.app.report

import com.financetracker.api.mail.MailDTO
import com.financetracker.api.mail.Template
import com.financetracker.app.expense.ExpenseEntity
import com.financetracker.app.mail.MailService
import com.financetracker.app.report.generators.GeneralMonthlyReportsGenerator
import com.financetracker.app.report.generators.GeneralWeeklyReportsGenerator
import com.financetracker.app.report.types.DateInterval
import com.financetracker.app.report.types.GeneralMonthlyReport
import com.financetracker.app.report.types.GeneralWeeklyReport
import com.financetracker.app.report.types.Report
import com.financetracker.app.user.Role
import com.financetracker.app.user.UserEntity
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalDate

class ReportsExecutorTest extends Specification {

    MailService mailService = Mock()
    GeneralWeeklyReportsGenerator generalWeeklyReportsGenerator = Mock()
    GeneralMonthlyReportsGenerator generalMonthlyReportsGenerator = Mock()

    ReportsExecutor executor = new ReportsExecutor(List.of(generalMonthlyReportsGenerator, generalWeeklyReportsGenerator), mailService)

    def "should execute monthly reports"() {
        given:
        List<GeneralMonthlyReport> reports = List.of(getMonthlyReport(), getMonthlyReport())
        MailDTO mail = GroovyMock()
        Template template = Template.GENERAL_MONTHLY_REPORT

        when:
        executor.executeMonthlyReports()

        then:
        1 * generalMonthlyReportsGenerator.generate() >> reports
        0 * generalWeeklyReportsGenerator.generate()
        1 * generalMonthlyReportsGenerator.getTemplate() >> template
        2 * mailService.createMail(_ as Report, template, template.title) >> mail
        2 * mailService.sendMail(mail)
    }

    def "should execute weekly reports"() {
        given:
        List<GeneralWeeklyReport> reports = List.of(getWeeklyReport(), getWeeklyReport())
        MailDTO mail = GroovyMock()
        Template template = Template.GENERAL_WEEKLY_REPORT

        when:
        executor.executeWeeklyReports()

        then:
        1 * generalWeeklyReportsGenerator.generate() >> reports
        0 * generalMonthlyReportsGenerator.generate()
        1 * generalWeeklyReportsGenerator.getTemplate() >> template
        2 * mailService.createMail(_ as Report, template, template.title) >> mail
        2 * mailService.sendMail(mail)
    }

    def "should stop execute weekly reports if list of generated mails is empty"() {
        given:
        List<GeneralWeeklyReport> reports = List.of()
        MailDTO mail = GroovyMock()
        Template template = Template.GENERAL_WEEKLY_REPORT

        when:
        executor.executeWeeklyReports()

        then:
        1 * generalWeeklyReportsGenerator.generate() >> reports
        0 * generalMonthlyReportsGenerator.generate()
        1 * generalWeeklyReportsGenerator.getTemplate() >> template
        0 * mailService.createMail(_ as Report, template, template.title)
        0 * mailService.sendMail(mail)
    }

    private GeneralMonthlyReport getMonthlyReport() {
        return GeneralMonthlyReport.builder()
            .user(getUser())
            .dateInterval(new DateInterval(LocalDate.of(2023, 9, 1), LocalDate.of(2023, 9, 30)))
            .totalExpenses(564.34)
            .largestExpense(new ExpenseEntity("1", LocalDate.of(2023, 9, 5), null, 122.22, null))
            .averageWeeklyExpense(115.24)
            .weekWithHighestExpenses(new DateInterval(LocalDate.of(2023, 9, 4), LocalDate.of(2023, 9, 10)))
            .dayWithHighestAverageExpense(DayOfWeek.TUESDAY)
            .totalIncomes(1200.50)
            .budgetSummary(636.16)
            .build()
    }

    private GeneralWeeklyReport getWeeklyReport() {
        return GeneralWeeklyReport.builder()
            .user(getUser())
            .dateInterval(new DateInterval(LocalDate.of(2023, 9, 1), LocalDate.of(2023, 9, 30)))
            .totalExpenses(564.34)
            .largestExpense(new ExpenseEntity("1", LocalDate.of(2023, 9, 5), null, 122.22, null))
            .averageDailyExpense(35.22)
            .totalIncomes(1200.50)
            .budgetSummary(636.16)
            .build()
    }

    private UserEntity getUser() {
        return new UserEntity("1", "Anne", "anne@gmail.com", "anne123", List.of(Role.USER))
    }
}
