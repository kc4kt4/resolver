package ru.kc4kt4.resolver.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.dto.CompanyDTO;
import ru.kc4kt4.resolver.dto.IndividualDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.enums.ApplicationStatus;
import ru.kc4kt4.resolver.exception.InternalServerError;
import ru.kc4kt4.resolver.repository.ApplicationRepository;
import ru.kc4kt4.resolver.request.AcceptApplicationRequest;
import ru.kc4kt4.resolver.response.SuccessfulResponse;
import ru.kc4kt4.resolver.service.MapperService;

@Service
@Slf4j
public class AcceptApplicationHandler {

    private final ApplicationRepository applicationRepository;
    private final MapperService mapperService;

    @Autowired
    AcceptApplicationHandler(ApplicationRepository applicationRepository,
                             MapperService mapperService) {
        this.applicationRepository = applicationRepository;
        this.mapperService = mapperService;
    }

    public SuccessfulResponse handleRequest(AcceptApplicationRequest request) {
        try {
            Application application;
            ApplicationDTO dto = request.getApplication();
            if (dto instanceof CompanyDTO) {
                CompanyDTO companyDTO = (CompanyDTO) dto;
                log.debug(String.format("Получена заявка от компании - %s", companyDTO.getCompanyName()));
                application = mapperService.convertToEntity(companyDTO);
            } else {
                IndividualDTO individualDTO = (IndividualDTO) dto;
                log.debug(String.format("Получена заявка физического лица с номером телефона - %s",
                                        individualDTO.getPhone()));
                application = mapperService.convertToEntity(individualDTO);
            }
            log.debug("Заявка успешно обработана");
            applicationRepository.save(application);
            log.debug("Заявка успешно сохранена в базу");

            SuccessfulResponse response = new SuccessfulResponse();
            response.setStatus(ApplicationStatus.ACCEPTED);
            return response;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalServerError("Something went wrong!");
        }
    }
}
