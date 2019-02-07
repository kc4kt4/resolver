package ru.kc4kt4.resolver.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kc4kt4.resolver.AbstractTest;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.dto.CompanyDTO;
import ru.kc4kt4.resolver.dto.IndividualDTO;
import ru.kc4kt4.resolver.entity.Company;
import ru.kc4kt4.resolver.entity.Individual;
import ru.kc4kt4.resolver.enums.ApplicationType;
import ru.kc4kt4.resolver.repository.ApplicationRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author vasilevsky.ii on 07.02.2019
 */

public class RabbitMQHandleMessageServiceTest extends AbstractTest {

    private static final String NAME = "Jasper";
    private static final String SURNAME = "Report";
    private static final String COMPANY_NAME = "SPRING";
    private static final String PHONE = "+78889991234";

    @Autowired
    private RabbitMQHandleMessageService rabbitMQHandleMessageService;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    public void receiveMessageTest() throws Exception {
        rabbitMQHandleMessageService.receiveMessage(createCompanyDto());

        assertEquals(1, applicationRepository.findAll().size());
        assertTrue(applicationRepository.findAll().get(0) instanceof Company);
        Company company = (Company) applicationRepository.findAll().get(0);
        assertEquals(NAME, company.getDirectorName());
        assertEquals(SURNAME, company.getDirectorSurname());
        assertEquals(COMPANY_NAME, company.getCompanyName());

        rabbitMQHandleMessageService.receiveMessage(createIndividualDto());
        assertEquals(2, applicationRepository.findAll().size());
        assertTrue(applicationRepository.findAll().get(1) instanceof Individual);
        Individual individual = (Individual) applicationRepository.findAll().get(1);
        assertEquals(NAME, individual.getName());
        assertEquals(SURNAME, individual.getSurname());
        assertEquals(PHONE, individual.getPhone());
    }

    private ApplicationDTO createCompanyDto() {
        CompanyDTO dto = new CompanyDTO();
        dto.setType(ApplicationType.COMPANY.name());
        dto.setDirectorName(NAME);
        dto.setDirectorSurname(SURNAME);
        dto.setCompanyName(COMPANY_NAME);
        return dto;
    }

    private ApplicationDTO createIndividualDto() {
        IndividualDTO dto = new IndividualDTO();
        dto.setType(ApplicationType.INDIVIDUAL.name());
        dto.setName(NAME);
        dto.setSurname(SURNAME);
        dto.setPhone(PHONE);
        return dto;
    }
}
