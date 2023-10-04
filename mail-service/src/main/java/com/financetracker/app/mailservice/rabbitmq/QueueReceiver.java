package com.financetracker.app.mailservice.rabbitmq;

import com.financetracker.api.mail.MailDTO;
import com.financetracker.app.mailservice.mail.MailCreator;
import com.financetracker.app.mailservice.mail.MailDeliverer;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "${spring.rabbitmq.queue}")
@RequiredArgsConstructor
public class QueueReceiver {

    private final MailCreator mailCreator;
    private final MailDeliverer mailDeliverer;

    @RabbitHandler
    public void receive(@Payload MailDTO mail) {
        MimeMessage mailToSend = mailCreator.createMailToSend(mail);
        mailDeliverer.sendMail(mailToSend);
    }
}