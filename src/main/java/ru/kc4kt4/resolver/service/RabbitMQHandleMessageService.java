package ru.kc4kt4.resolver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.exception.ProcessApplicationException;

/**
 * @author vasilevsky.ii on 06.02.2019
 */
@Component
@Slf4j
public class RabbitMQHandleMessageService {

    private final ProcessingService processingService;

    public RabbitMQHandleMessageService(ProcessingService processingService) {
        this.processingService = processingService;
    }

    public void receiveMessage(ApplicationDTO message) {
        log.debug(String.format("LOG_MQ_HANDLE_MESSAGE_SERVICE: Принято сообщение %s, начало обработки", message.toString()));
        ApplicationDTO processedMessage;
        try {
            processedMessage = processingService.process(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ProcessApplicationException("Ошибка обработки заявки");
        }
        Application application = processingService.saveApplication(processedMessage);
        log.trace(String.format("LOG_MQ_HANDLE_MESSAGE_SERVICE: Заявка %s успешно обработана и сохранена", application));
    }
}
