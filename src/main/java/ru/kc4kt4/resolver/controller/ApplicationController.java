package ru.kc4kt4.resolver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kc4kt4.resolver.handler.AcceptApplicationHandler;
import ru.kc4kt4.resolver.handler.GiveApplicationByIdHandler;
import ru.kc4kt4.resolver.request.AcceptApplicationRequest;
import ru.kc4kt4.resolver.response.SuccessfulResponse;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/application")
public class ApplicationController {

    @Autowired
    private AcceptApplicationHandler acceptApplicationHandler;
    @Autowired
    private GiveApplicationByIdHandler giveApplicationByIdHandler;

    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SuccessfulResponse> acceptApplication(@RequestBody @Valid AcceptApplicationRequest request) {
        SuccessfulResponse response = acceptApplicationHandler.handleRequest(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SuccessfulResponse giveApplicationById(@PathVariable(value = "id") Long id) {
        return giveApplicationByIdHandler.handlerRequest(id);
    }
}
