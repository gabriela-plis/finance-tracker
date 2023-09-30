package com.financetracker.app.rabbitmq;

import com.financetracker.api.mail.MailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    public void send(MailDTO mail) {
        rabbitTemplate.convertAndSend(exchange, mail);
    }
}
