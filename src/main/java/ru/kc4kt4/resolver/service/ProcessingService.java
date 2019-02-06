package ru.kc4kt4.resolver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.repository.ApplicationRepository;

@Service
@Slf4j
public class ProcessingService {

    private final ApplicationRepository applicationRepository;
    private final MapperService mapperService;

    public ProcessingService(ApplicationRepository applicationRepository,
                             MapperService mapperService) {
        this.applicationRepository = applicationRepository;
        this.mapperService = mapperService;
    }

    public ApplicationDTO process(ApplicationDTO message) throws Exception {
        log.debug(String.format("LOG_PROCESSING_SERVICE: Обработка заявки %s", message.toString()));
        Thread.sleep(1000);
        log.debug(String.format("LOG_PROCESSING_SERVICE: Заявка успешно обработана %s", message.toString()));
        return message;
    }

    public Application saveApplication(ApplicationDTO processedMessage) {
        Application application = mapperService.convertToEntity(processedMessage);
        return applicationRepository.save(application);
    }
}
