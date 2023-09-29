package com.financetracker.app.mail

import com.financetracker.api.mail.MailDTO
import com.financetracker.api.mail.Template
import com.financetracker.app.rabbitmq.QueueSender
import com.financetracker.app.security.authorization.Role
import com.financetracker.app.user.User
import spock.lang.Specification

class MailServiceTest extends Specification {

    QueueSender queueSender = Mock()

    private final MailService mailService = new MailService(queueSender)

    def "should create greeting email"() {
        given:
        User user = new User("1", "Anne", "anne@gmail.com", "anne123", List.of(Role.USER))
        Template template = Template.GREETING

        when:
        MailDTO result = mailService.createGreetingMail(user)

        then:
        with(result) {
            template == template
            recipient() == "anne@gmail.com"
            title() == template.title
            templateProperties().size() == 1
            with(templateProperties()) {
                username == "Anne"
            }
        }

    }

    def "should throw IllegalArgumentException if user is null for create greeting email"() {
        given:
        User user = null

        when:
        mailService.createGreetingMail(user)

        then:
        thrown(IllegalArgumentException)
    }

    def "should send mail"() {
        given:
        MailDTO mail = GroovyMock()

        when:
        mailService.sendMail(mail)

        then:
        1 * queueSender.send(mail)
    }

    def "should throw IllegalArgumentException if mail to send is null"() {
        given:
        MailDTO mail = null

        when:
        mailService.sendMail(mail)

        then:
        thrown(IllegalArgumentException)
    }
}
