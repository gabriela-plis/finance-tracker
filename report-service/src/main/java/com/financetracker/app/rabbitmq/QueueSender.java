package com.financetracker.app.rabbitmq;

import com.financetracker.app.report.types.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueSender {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void send(Report report) {
        rabbitTemplate.convertAndSend(queue.getName(), report);
    }
}
