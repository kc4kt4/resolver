package ru.kc4kt4.resolver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.util.Constants;

@Slf4j
@Component
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ApplicationDTO application) {
        log.debug(String.format("LOG_MQ_SERVICE: Отправка заявки %s", application.toString()));
        rabbitTemplate.convertAndSend(Constants.TOPIC_EXCHANGE_NAME, "resolver", application);
        log.debug(String.format("LOG_MQ_SERVICE: Сообщение %s успешно отправлено", application.toString()));
    }

}
