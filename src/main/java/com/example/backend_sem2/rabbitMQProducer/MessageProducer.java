package com.example.backend_sem2.rabbitMQProducer;

import com.example.backend_sem2.model.RabbitMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.example.backend_sem2.config.RabbitMQConfig.EXCHANGE_MESSAGES;
import static com.example.backend_sem2.config.RabbitMQConfig.QUEUE_MESSAGES;


@Service
public class MessageProducer {

    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
    private int messageNumber = 0;
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(RabbitMQMessage rabbitMQMessage) {
        log.info("Sending message...");

        rabbitTemplate.convertAndSend(EXCHANGE_MESSAGES, QUEUE_MESSAGES, rabbitMQMessage);
        log.info(String.format("Message have been sent to \"%s\" Queue in \"%s\" Exchange",
                QUEUE_MESSAGES, EXCHANGE_MESSAGES
        ));
    }
}