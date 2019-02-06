package ru.kc4kt4.resolver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.dto.CompanyDTO;
import ru.kc4kt4.resolver.dto.IndividualDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.entity.Company;
import ru.kc4kt4.resolver.entity.Individual;
import ru.kc4kt4.resolver.enums.ApplicationType;
import ru.kc4kt4.resolver.exception.MapperServiceException;

@Service
@Slf4j
public class MapperService {

    public Application convertToEntity(ApplicationDTO dto) {
        ApplicationType applicationType = ApplicationType.valueOf(dto.getType());
        switch (applicationType) {
            case COMPANY:
                CompanyDTO companyDTO = (CompanyDTO) dto;
                log.debug("Receiver application from company - {}", dto);
                return convertToEntity(companyDTO);
            case INDIVIDUAL:
                IndividualDTO individualDTO = (IndividualDTO) dto;
                log.debug("Receiver application from individual - {}", dto);
                return convertToEntity(individualDTO);
            default:
                throw new MapperServiceException("Mapping to dto error");
        }
    }

    public ApplicationDTO convertToDTO(Application application) {
        if (application instanceof Company) {
            Company company = (Company) application;
            log.debug("Application {} instance of Company", application);
            return convertToDTO(company);
        } else if (application instanceof Individual) {
            Individual individual = (Individual) application;
            log.debug("Application {} instance of Individual", application);
            return convertToDTO(individual);
        } else {
            throw new MapperServiceException("Mapping to entity error");
        }
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
        return String.format("Mapping error with object - %s", o);
    }
}
