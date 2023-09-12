package com.financetracker.app.report.db

import com.financetracker.app.user.Role
import com.financetracker.app.user.UserEntity
import com.financetracker.app.utils.DocumentNotFoundException
import spock.lang.Specification

import static com.financetracker.app.report.db.ReportType.*

class ReportTypeServiceTest extends Specification {

    ReportTypeRepository reportRepository = Mock()

    ReportTypeService reportTypeService = new ReportTypeService(reportRepository)

    def "should return report"() {
        given:
        ReportType reportType = GENERAL_MONTHLY_REPORT
        ReportTypeEntity report = getReport()

        when:
        ReportTypeEntity result = reportTypeService.getReport(reportType)

        then:
        1 * reportRepository.findByName(reportType) >>  Optional.of(report)

        and:
        with(result) {
            id == "1"
            name == GENERAL_MONTHLY_REPORT
            subscribers.size() == 1
        }
    }

    def "should throw DocumentNotFoundException when searching report doesn't exist"() {
        given:
        ReportType reportType = GENERAL_MONTHLY_REPORT

        when:
        reportTypeService.getReport(reportType)

        then:
        1 * reportRepository.findByName(reportType) >> Optional.empty()

        and:
        thrown(DocumentNotFoundException)
    }

    def "should get report subscribers"() {
        given:
        ReportType reportType = GENERAL_MONTHLY_REPORT
        ReportTypeEntity report = getReport()

        when:
        List<UserEntity> result = reportTypeService.getReportSubscribers(reportType)

        then:
        1 * reportRepository.findByName(reportType) >> Optional.of(report)

        and:
        with(result) {
            result.size() == 1
            with(result[0]) {
                id == "1"
                username == "Anne"
                email == "anne@gmail.com"
                password == "abc"
                roles.size() == 1
            }
        }
    }

    private ReportTypeEntity getReport() {
        UserEntity subscriber = new UserEntity("1", "Anne", "anne@gmail.com", "abc", List.of(Role.USER))
        return new ReportTypeEntity("1", GENERAL_MONTHLY_REPORT, List.of(subscriber))
    }
}
