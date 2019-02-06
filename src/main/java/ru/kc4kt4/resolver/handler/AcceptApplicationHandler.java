package ru.kc4kt4.resolver.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.enums.ApplicationStatus;
import ru.kc4kt4.resolver.exception.InternalServerError;
import ru.kc4kt4.resolver.response.SuccessfulResponse;
import ru.kc4kt4.resolver.service.RabbitMQService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AcceptApplicationHandler {

    private final RabbitMQService rabbitMQService;

    public SuccessfulResponse handleRequest(ApplicationDTO dto) {
        try {
            rabbitMQService.sendMessage(dto);

            return createSuccessfulResponse();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalServerError("Something went wrong!");
        }
    }

    private SuccessfulResponse createSuccessfulResponse() {
        SuccessfulResponse response = new SuccessfulResponse();
        response.setStatus(ApplicationStatus.ACCEPTED);
        return response;
    }
}
