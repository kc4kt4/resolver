package ru.kc4kt4.resolver.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.enums.ApplicationStatus;
import ru.kc4kt4.resolver.exception.InternalServerError;
import ru.kc4kt4.resolver.service.RabbitMQService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AcceptApplicationHandler {

    private final RabbitMQService rabbitMQService;

    public ApplicationStatus handleRequest(ApplicationDTO dto) {
        try {
            rabbitMQService.sendMessage(dto);
            return ApplicationStatus.ACCEPTED;
        } catch (Exception e) {
            log.error("Sending message to rabbit exception", e);
            throw new InternalServerError("Something went wrong!");
        }
    }
}
