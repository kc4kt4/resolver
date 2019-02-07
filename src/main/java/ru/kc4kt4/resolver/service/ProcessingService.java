package ru.kc4kt4.resolver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.repository.ApplicationRepository;

/**
 * The type Processing service.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessingService {

    private final ApplicationRepository applicationRepository;
    private final MapperService mapperService;

    /**
     * Process application dto.
     *
     * @param message the message
     * @return the application dto
     * @throws Exception the exception
     */
    public ApplicationDTO process(ApplicationDTO message) throws Exception {
        log.debug("Application {} process started", message);
        Thread.sleep(1000);
        log.debug("Application {} process ended", message);
        return message;
    }

    /**
     * Save application application.
     *
     * @param processedMessage the processed message
     * @return the application
     */
    public Application saveApplication(ApplicationDTO processedMessage) {
        Application application = mapperService.convertToEntity(processedMessage);
        return applicationRepository.save(application);
    }
}
