package com.financetracker.app.mail;

import com.financetracker.app.rabbitmq.QueueSender;
import com.financetracker.app.report.types.Report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.Arrays.stream;

@Component
@RequiredArgsConstructor
public class MailService {

    private final QueueSender queueSender;

    public <T extends Report> MailDTO createMail(T report, Template template, String title) {
        Field[] fields = report.getClass().getDeclaredFields();

        Map<String, Object> properties = stream(fields)
            .collect(Collectors.toMap(Field::getName, field -> getFieldValue(field, report)));

        return MailDTO.builder()
            .template(template)
            .recipient(report.user().getEmail())
            .title(title)
            .templateProperties(properties)
            .build();
    }

    public void sendMail(MailDTO mail) {
        queueSender.send(mail);
    }

    private <T> Object getFieldValue(Field field, T report) {
        try {
            return field.get(report);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}