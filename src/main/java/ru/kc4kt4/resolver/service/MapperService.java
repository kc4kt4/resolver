package ru.kc4kt4.resolver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.dto.CompanyDTO;
import ru.kc4kt4.resolver.dto.IndividualDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.entity.Company;
import ru.kc4kt4.resolver.entity.Individual;
import ru.kc4kt4.resolver.exception.MapperServiceException;

@Service
@Slf4j
public class MapperService {

    public Application convertToEntity(ApplicationDTO dto) {
        Application application;
        if (dto instanceof CompanyDTO) {
            CompanyDTO companyDTO = (CompanyDTO) dto;
            log.debug(String.format("LOG_MAPPER: Получена заявка от компании - %s", companyDTO.getCompanyName()));
            application = convertToEntity(companyDTO);
        } else {
            IndividualDTO individualDTO = (IndividualDTO) dto;
            log.debug(String.format("LOG_MAPPER: Получена заявка физического лица с номером телефона - %s",
                                    individualDTO.getPhone()));
            application = convertToEntity(individualDTO);
        }
        return application;
    }

    public ApplicationDTO convertToDTO(Application application) {
        ApplicationDTO dto;
        if (application instanceof Company) {
            log.debug(String.format("LOG_MAPPER: Заявка с id %s относится к заявках от компаний",
                                    application.getApplicationId()));
            Company company = (Company) application;
            dto = convertToDTO(company);
        } else {
            log.debug(String.format("LOG_MAPPER: Заявка с id %s относится к заявках от физических лиц",
                                    application.getApplicationId()));
            Individual individual = (Individual) application;
            dto = convertToDTO(individual);
        }
        return dto;
    }

    private Application convertToEntity(CompanyDTO dto) {
        try {
            Company company = new Company();
            company.setDirectorName(dto.getDirectorName());
            company.setDirectorSurname(dto.getDirectorSurname());
            company.setCompanyName(dto.getCompanyName());
            return company;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MapperServiceException(createErrorMessage(dto));
        }
    }

    private Application convertToEntity(IndividualDTO dto) {
        try {
        Individual individual = new Individual();
        individual.setName(dto.getName());
        individual.setSurname(dto.getSurname());
        individual.setPhone(dto.getPhone());
        return individual;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MapperServiceException(createErrorMessage(dto));
        }
    }

    private IndividualDTO convertToDTO(Individual entity) {
        try {
        IndividualDTO dto = new IndividualDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setId(entity.getApplicationId());
        return dto;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MapperServiceException(createErrorMessage(entity));
        }
    }

    private CompanyDTO convertToDTO(Company entity) {
        try {
            CompanyDTO dto = new CompanyDTO();
            dto.setDirectorName(entity.getDirectorName());
            dto.setDirectorSurname(entity.getDirectorSurname());
            dto.setCompanyName(entity.getCompanyName());
            dto.setId(entity.getApplicationId());
            return dto;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MapperServiceException(createErrorMessage(entity));
        }
    }

    private String createErrorMessage(Object o) {
        return String.format("LOG_MAPPER: Ошибка при попытке конвертации %s", o.toString());
    }
}
