package ru.kc4kt4.resolver.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@Api("Api: application")
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private AcceptApplicationHandler acceptApplicationHandler;
    @Autowired
    private GiveApplicationByIdHandler giveApplicationByIdHandler;

    @ApiOperation(value = "Принять заявку на обработку")
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SuccessfulResponse acceptApplication(@RequestBody @Valid AcceptApplicationRequest request) {
        return acceptApplicationHandler.handleRequest(request);
    }

    @ApiOperation(value = "Получить заявку по id")
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SuccessfulResponse giveApplicationById(@PathVariable(value = "id") Long id) {
        return giveApplicationByIdHandler.handlerRequest(id);
    }
}
