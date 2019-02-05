package ru.kc4kt4.resolver.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.kc4kt4.resolver.dto.ApplicationDTO;

import javax.validation.Valid;

import java.io.Serializable;

@Data
public class GetApplicationByIdRequest implements Serializable {

    @ApiModelProperty("Заявка")
    @JsonIgnoreProperties(value = {"id"})
    @Valid
    private ApplicationDTO application;
}
