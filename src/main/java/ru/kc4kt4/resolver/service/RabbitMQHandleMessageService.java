package ru.kc4kt4.resolver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.exception.ProcessApplicationException;

/**
 * The type Rabbit mq handle message service.
 *
 * @author vasilevsky.ii on 06.02.2019
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQHandleMessageService {

    private final ProcessingService processingService;

    /**
     * Receive message.
     *
     * @param message the message
     */
    public void receiveMessage(ApplicationDTO message) {
        log.debug("Message {} is receive, start processing");
        ApplicationDTO processedMessage;
        try {
            processedMessage = processingService.process(message);
        } catch (Exception e) {
            log.error("Processing application exception", e);
            throw new ProcessApplicationException("Error with process application");
        }
        Application application = processingService.saveApplication(processedMessage);
        log.debug("Application {} successful processed and save", application);
    }
}
