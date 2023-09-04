package com.financetracker.app.mail;

import com.financetracker.api.mail.MailDTO;
import com.financetracker.api.mail.Template;
import com.financetracker.app.rabbitmq.QueueSender;
import com.financetracker.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailService {

    private final QueueSender queueSender;

    public MailDTO createGreetingMail(User user, Template template, String title) {

        Map<String, Object> properties = Map.of(
            "username", user.getUsername()
        );

        return MailDTO.builder()
            .template(template)
            .recipient(user.getEmail())
            .title(title)
            .templateProperties(properties)
            .build();
    }

    public void sendMail(MailDTO mail) {
        queueSender.send(mail);
    }

}