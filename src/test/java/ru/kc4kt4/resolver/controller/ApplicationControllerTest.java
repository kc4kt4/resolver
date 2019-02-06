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
import ru.kc4kt4.resolver.entity.Company;
import ru.kc4kt4.resolver.entity.Individual;
import ru.kc4kt4.resolver.enums.ApplicationStatus;
import ru.kc4kt4.resolver.repository.ApplicationRepository;
import ru.kc4kt4.resolver.request.AcceptApplicationRequest;
import ru.kc4kt4.resolver.response.SuccessfulResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author vasilevsky.ii on 06.02.2019
 */

public class ApplicationControllerTest extends AbstractTest {

    private final static String URL = "/application/";
    private static final String NAME = "Peter";
    private static final String SURNAME = "Jackson";
    private static final String COMPANY = "VTB";
    private static final String PHONE = "+79990000001";

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
        Thread.sleep(1000);
        assertEquals(1, applicationRepository.findAll().size());
        assertTrue(applicationRepository.findById(1L).isPresent());
        assertTrue(applicationRepository.findById(1L).get() instanceof Company);
    }

    @Test
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
        Thread.sleep(1000);
        assertEquals(2, applicationRepository.findAll().size());
        assertTrue(applicationRepository.findById(2L).isPresent());
        assertTrue(applicationRepository.findById(2L).get() instanceof Individual);

    }

    private String createURLWithPort(String url) {
        return "http://localhost:" + port + url;
    }

    private HttpEntity createHttpEntity(ApplicationDTO dto) {
        return new HttpEntity<>(createAcceptApplicationRequest(dto), createSimpleHeaders());
    }

    private AcceptApplicationRequest createAcceptApplicationRequest(ApplicationDTO dto) {
        AcceptApplicationRequest request = new AcceptApplicationRequest();
        request.setApplication(dto);
        return request;
    }

    private ApplicationDTO createCompanyDTO() {
        CompanyDTO dto = new CompanyDTO();
        dto.setDirectorName(NAME);
        dto.setDirectorSurname(SURNAME);
        dto.setCompanyName(COMPANY);
        return dto;
    }

    private ApplicationDTO createIndividualDTO() {
        IndividualDTO dto = new IndividualDTO();
        dto.setName(NAME);
        dto.setSurname(SURNAME);
        dto.setPhone(PHONE);
        return dto;
    }

    private HttpHeaders createSimpleHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

}
