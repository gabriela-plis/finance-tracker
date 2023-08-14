package com.financetracker.app.mailservice.rabbitmq;

import com.financetracker.app.mailservice.MailDTO;
import com.financetracker.app.mailservice.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "")
@RequiredArgsConstructor
public class QueueReceiver {

    private final MailService mailService;

    @RabbitHandler
    public void receive(@Payload MailDTO mail) {
        mailService.sendMail(mail);
    }
}