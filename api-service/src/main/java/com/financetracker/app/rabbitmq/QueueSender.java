package com.financetracker.app.rabbitmq;

import com.financetracker.api.mail.MailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueSender {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void send(MailDTO mail) {
        rabbitTemplate.convertAndSend(queue.getName(), mail);
    }
}
