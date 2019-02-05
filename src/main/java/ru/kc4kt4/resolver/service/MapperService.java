package ru.kc4kt4.resolver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kc4kt4.resolver.dto.CompanyDTO;
import ru.kc4kt4.resolver.dto.IndividualDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.entity.Company;
import ru.kc4kt4.resolver.entity.Individual;
import ru.kc4kt4.resolver.exception.MapperServiceException;

@Service
@Slf4j
public class MapperService {

    public Application convertToEntity(CompanyDTO dto) {
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

    public Application convertToEntity(IndividualDTO dto) {
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

    public IndividualDTO convertToDTO(Individual entity) {
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

    public CompanyDTO convertToDTO(Company entity) {
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
        return String.format("Ошибка при попытке конвертации %s", o.toString());
    }
}
