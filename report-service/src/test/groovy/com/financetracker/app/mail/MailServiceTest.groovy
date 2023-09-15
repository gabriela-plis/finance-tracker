package com.financetracker.app.mail

import com.financetracker.api.mail.MailDTO
import com.financetracker.api.mail.Template
import com.financetracker.app.expense.ExpenseEntity
import com.financetracker.app.rabbitmq.QueueSender
import com.financetracker.app.report.types.DateInterval
import com.financetracker.app.report.types.GeneralMonthlyReport
import com.financetracker.app.user.Role
import com.financetracker.app.user.UserEntity
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalDate

class MailServiceTest extends Specification {

    QueueSender queueSender = Mock()

    MailService mailService = new MailService(queueSender)

    def "should create mail"() {
        given:
        GeneralMonthlyReport generalMonthlyReport = getReport()
        Template template = Template.GENERAL_MONTHLY_REPORT
        String title = "Title"

        when:
        MailDTO result = mailService.createMail(generalMonthlyReport, template, title)

        then:
        with(result) {
            template == Template.GENERAL_MONTHLY_REPORT
            recipient() == "anne@gmail.com"
            title == "Title"
            with(templateProperties()) {
                with(user) {
                    id == "1"
                    username == "Anne"
                    email == "anne@gmail.com"
                    password == "anne123"
                    roles.size() == 1
                    roles.get(0) == Role.USER
                }
                with(dateInterval) {
                    startDate == LocalDate.of(2023, 9, 1)
                    endDate == LocalDate.of(2023, 9, 30)
                }
                totalExpenses == 564.34
                with(largestExpense) {
                    date == LocalDate.of(2023, 9, 5)
                    price == 122.22
                }
                averageWeeklyExpense == 115.24
                with(weekWithHighestExpenses) {
                    startDate == LocalDate.of(2023, 9, 4)
                    endDate == LocalDate.of(2023, 9, 10)
                }
                dayWithHighestAverageExpense == DayOfWeek.TUESDAY
                totalIncomes == 1200.50
                budgetSummary == 636.16
            }
        }
    }

    def "should send email"() {
        given:
        MailDTO mail = GroovyMock()

        when:
        mailService.sendMail(mail)

        then:
        1 * queueSender.send(mail)
    }

    private GeneralMonthlyReport getReport() {
        return GeneralMonthlyReport.builder()
            .user(new UserEntity("1", "Anne", "anne@gmail.com", "anne123", List.of(Role.USER)))
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
}
