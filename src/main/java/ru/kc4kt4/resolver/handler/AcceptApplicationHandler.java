package ru.kc4kt4.resolver.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.enums.ApplicationStatus;
import ru.kc4kt4.resolver.exception.InternalServerError;
import ru.kc4kt4.resolver.request.AcceptApplicationRequest;
import ru.kc4kt4.resolver.response.SuccessfulResponse;
import ru.kc4kt4.resolver.service.RabbitMQService;

@Service
@Slf4j
public class AcceptApplicationHandler {

    private final RabbitMQService rabbitMQService;

    @Autowired
    public AcceptApplicationHandler(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    public SuccessfulResponse handleRequest(AcceptApplicationRequest request) {
        try {
            ApplicationDTO dto = request.getApplication();

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
