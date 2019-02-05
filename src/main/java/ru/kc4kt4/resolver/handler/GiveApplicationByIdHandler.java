package ru.kc4kt4.resolver.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.entity.Company;
import ru.kc4kt4.resolver.entity.Individual;
import ru.kc4kt4.resolver.enums.ApplicationStatus;
import ru.kc4kt4.resolver.exception.ApplicationNotFoundException;
import ru.kc4kt4.resolver.repository.ApplicationRepository;
import ru.kc4kt4.resolver.response.SuccessfulResponse;
import ru.kc4kt4.resolver.service.MapperService;

@Service
@Slf4j
public class GiveApplicationByIdHandler {

    private final ApplicationRepository applicationRepository;
    private final MapperService mapperService;

    @Autowired
    public GiveApplicationByIdHandler(ApplicationRepository applicationRepository,
                                      MapperService mapperService) {
        this.applicationRepository = applicationRepository;
        this.mapperService = mapperService;
    }

    public SuccessfulResponse handlerRequest(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(String.format("Заявка с id %s не найдена", id)));
        log.debug(String.format("Заявка с id %s найдена", id));

        ApplicationDTO dto;
        if (application instanceof Company) {
            log.debug(String.format("Заявка с id %s относится к заявках от компаний", id));
            Company company = (Company) application;
            dto = mapperService.convertToDTO(company);
        } else {
            log.debug(String.format("Заявка с id %s относится к заявках от физических лиц", id));
            Individual individual = (Individual) application;
            dto = mapperService.convertToDTO(individual);
        }
        SuccessfulResponse response = new SuccessfulResponse();
        response.setStatus(ApplicationStatus.APPROVED);
        response.setApplication(dto);
        return response;
    }
}
