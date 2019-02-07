package ru.kc4kt4.resolver.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.kc4kt4.resolver.AbstractTest;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.dto.CompanyDTO;
import ru.kc4kt4.resolver.dto.IndividualDTO;
import ru.kc4kt4.resolver.entity.Application;
import ru.kc4kt4.resolver.entity.Company;
import ru.kc4kt4.resolver.entity.Individual;
import ru.kc4kt4.resolver.enums.ApplicationStatus;
import ru.kc4kt4.resolver.enums.ApplicationType;
import ru.kc4kt4.resolver.repository.ApplicationRepository;
import ru.kc4kt4.resolver.response.ErrorResponse;
import ru.kc4kt4.resolver.response.SuccessfulResponse;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author vasilevsky.ii on 06.02.2019
 */

public class ApplicationControllerTest extends AbstractTest {

    private final static String URL = "/application";
    private static final String NAME = "Peter";
    private static final String SURNAME = "Jackson";
    private static final String COMPANY = "VTB";
    private static final String PHONE = "+79990000001";
    private static final String ID = "1";
    private static final String BAD_ID = "123";

    @LocalServerPort
    protected int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    public void acceptCompanyApplicationTest() throws Exception {
        String url = createURLWithPort(URL);
        ResponseEntity<SuccessfulResponse> response = restTemplate.exchange(url,
                                                                            HttpMethod.POST,
                                                                            createHttpEntity(createCompanyDTO()),
                                                                            SuccessfulResponse.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ApplicationStatus.ACCEPTED, response.getBody().getStatus());
        assertEquals(0, applicationRepository.findAll().size());
        while (applicationRepository.findAll().size() == 0) {}
        assertEquals(1, applicationRepository.findAll().size());
        assertTrue(applicationRepository.findById(1L).isPresent());
        assertTrue(applicationRepository.findById(1L).get() instanceof Company);
    }

    @Test(timeout = 2000)
    @DependsOn("acceptCompanyApplicationTest")
    public void acceptIndividualApplicationTest() throws Exception {
        String url = createURLWithPort(URL);
        ResponseEntity<SuccessfulResponse> response = restTemplate.exchange(url,
                                                                            HttpMethod.POST,
                                                                            createHttpEntity(createIndividualDTO()),
                                                                            SuccessfulResponse.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ApplicationStatus.ACCEPTED, response.getBody().getStatus());
        assertEquals(1, applicationRepository.findAll().size());
        while (applicationRepository.findAll().size() == 1) {}
        assertEquals(2, applicationRepository.findAll().size());
        assertTrue(applicationRepository.findById(2L).isPresent());
        assertTrue(applicationRepository.findById(2L).get() instanceof Individual);
    }

    @Test
    @DependsOn("acceptCompanyApplicationTest")
    public void getApplicationByIdTest() {
        String url = createURLWithPort(URL + "/" + ID);
        ResponseEntity<SuccessfulResponse> response = restTemplate.exchange(url,
                                                                            HttpMethod.GET,
                                                                            createHttpEntity(),
                                                                            SuccessfulResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ApplicationStatus.APPROVED, response.getBody().getStatus());

        Optional<Application> app = applicationRepository.findById(Long.valueOf(ID));
        assertTrue(app.isPresent());
        assertTrue(app.get() instanceof Company);
        Company application = (Company) app.get();

        assertTrue(response.getBody().getApplication() instanceof CompanyDTO);
        CompanyDTO dto = (CompanyDTO) response.getBody().getApplication();

        assertEquals(application.getApplicationId(), dto.getId());
        assertEquals(application.getCompanyName(), dto.getCompanyName());
        assertEquals(application.getDirectorName(), dto.getDirectorName());
        assertEquals(application.getDirectorSurname(), dto.getDirectorSurname());
    }

    @Test
    public void getApplicationByIdTestWithBadId() {
        String url = createURLWithPort(URL + "/" + BAD_ID);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(url,
                                                                            HttpMethod.GET,
                                                                            createHttpEntity(),
                                                                            ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ApplicationStatus.NOT_READY_OR_REJECTED, response.getBody().getStatus());
    }

    private String createURLWithPort(String url) {
        return "http://localhost:" + port + url;
    }

    private HttpEntity createHttpEntity(ApplicationDTO dto) {
        return new HttpEntity<>(dto, createSimpleHeaders());
    }

    private HttpEntity createHttpEntity() {
        return new HttpEntity<>(null, createSimpleHeaders());
    }

    private ApplicationDTO createCompanyDTO() {
        CompanyDTO dto = new CompanyDTO();
        dto.setDirectorName(NAME);
        dto.setDirectorSurname(SURNAME);
        dto.setCompanyName(COMPANY);
        dto.setType(ApplicationType.COMPANY.name());
        return dto;
    }

    private ApplicationDTO createIndividualDTO() {
        IndividualDTO dto = new IndividualDTO();
        dto.setName(NAME);
        dto.setSurname(SURNAME);
        dto.setPhone(PHONE);
        dto.setType(ApplicationType.INDIVIDUAL.name());
        return dto;
    }

    private HttpHeaders createSimpleHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

}
