package com.financetracker.app.mailservice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(MailDTO mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(mail.recipient());
            helper.setSubject(mail.title());

            //define thymeleaf html template -> resources/templates/
            //create Context and setVariables(map <String, Object> properties)
            Context context = new Context();
            context.setVariables(mail.templateProperties());
            //create String object: templateEngine.process("template-name", context)
            String html = templateEngine.process("general-weekly-report-template", context);
            //set it as helper text
            helper.setText(html, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
