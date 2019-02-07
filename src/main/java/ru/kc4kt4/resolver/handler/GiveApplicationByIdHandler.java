package ru.kc4kt4.resolver.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.exception.ApplicationNotFoundException;
import ru.kc4kt4.resolver.repository.ApplicationRepository;
import ru.kc4kt4.resolver.response.SuccessfulResponse;
import ru.kc4kt4.resolver.service.MapperService;

/**
 * The type Give application by id handler.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GiveApplicationByIdHandler {

    private final ApplicationRepository applicationRepository;
    private final MapperService mapperService;

    /**
     * Handler request successful response.
     *
     * @param id the id
     * @return the successful response
     */
    public SuccessfulResponse handlerRequest(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(String.format("Заявка с id %s не найдена", id)));
        log.debug("Application with id {} not found", id);

        ApplicationDTO dto = mapperService.convertToDTO(application);

        SuccessfulResponse response = new SuccessfulResponse();
        response.setApplication(dto);
        return response;
    }
}
