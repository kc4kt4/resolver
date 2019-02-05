package ru.kc4kt4.resolver.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.kc4kt4.resolver.dto.ApplicationDTO;

import javax.validation.Valid;

import java.io.Serializable;

@Data
public class AcceptApplicationRequest implements Serializable {

    @ApiModelProperty("Заявка")
    @Valid
    private ApplicationDTO application;
}
