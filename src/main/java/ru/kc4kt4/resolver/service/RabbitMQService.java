package ru.kc4kt4.resolver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.util.Constants;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(ApplicationDTO application) {
        log.debug("Sending message in RabbitMQ");
        rabbitTemplate.convertAndSend(Constants.TOPIC_EXCHANGE_NAME, "resolver", application);
        log.debug("Message successfully send to RabbitMQ");
    }

}
