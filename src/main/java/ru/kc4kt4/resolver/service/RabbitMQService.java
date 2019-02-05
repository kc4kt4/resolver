package ru.kc4kt4.resolver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.exception.ProcessApplicationException;
import ru.kc4kt4.resolver.util.Constants;

@Component
@Slf4j
public class RabbitMQService {

    private final ProcessingService processingService;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitMQService(ProcessingService processingService, RabbitTemplate rabbitTemplate) {
        this.processingService = processingService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ApplicationDTO application) {
        log.debug(String.format("LOG_MQ_SERVICE: Отправка заявки %s", application.toString()));
        rabbitTemplate.convertAndSend(Constants.TOPIC_EXCHANGE_NAME, "resolver", application);
        log.debug(String.format("LOG_MQ_SERVICE: Сообщение %s успешно отправлено", application.toString()));
    }

    public void receiveMessage(ApplicationDTO message) {
        log.debug(String.format("LOG_MQ_SERVICE: Принято сообщение %s, начало обработки", message.toString()));
        ApplicationDTO processedMessage;
        try {
            processedMessage = processingService.process(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ProcessApplicationException("Ошибка обработки заявки");
        }
        Application application = processingService.saveApplication(processedMessage);
        log.trace(String.format("LOG_MQ_SERVICE: Заявка %s успешно обработана и сохранена", application));
    }
}
