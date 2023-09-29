package com.financetracker.app.mailservice.mail;

import com.financetracker.api.mail.MailDTO;
import com.financetracker.api.mail.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class MailCreator {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private final String sender;

    public MimeMessage createMailToSend(MailDTO mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(mail.recipient());
            helper.setSubject(mail.title());
            helper.setText(getHtmlTemplate(mail), true);

            return helper.getMimeMessage();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getHtmlTemplate(MailDTO mail) {
        Context context = new Context();
        context.setVariables(mail.templateProperties());
        return templateEngine.process(extractHtmlTemplateName(mail.template()), context);
    }

    private String extractHtmlTemplateName(Template template) {
        return template.toString().toLowerCase().replaceAll("_", "-") + "-template";
    }
}
