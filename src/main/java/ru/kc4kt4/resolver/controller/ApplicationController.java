package ru.kc4kt4.resolver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.enums.ApplicationStatus;
import ru.kc4kt4.resolver.handler.AcceptApplicationHandler;
import ru.kc4kt4.resolver.handler.GiveApplicationByIdHandler;
import ru.kc4kt4.resolver.response.SuccessfulResponse;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final AcceptApplicationHandler acceptApplicationHandler;
    private final GiveApplicationByIdHandler giveApplicationByIdHandler;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ApplicationStatus> acceptApplication(@RequestBody @Valid ApplicationDTO dto) {
        ApplicationStatus status = acceptApplicationHandler.handleRequest(dto);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SuccessfulResponse giveApplicationById(@PathVariable(value = "id") Long id) {
        return giveApplicationByIdHandler.handlerRequest(id);
    }
}
