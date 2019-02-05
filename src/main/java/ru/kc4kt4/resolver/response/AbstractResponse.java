package ru.kc4kt4.resolver.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.kc4kt4.resolver.enums.ApplicationStatus;

import java.io.Serializable;

@Data
public class AbstractResponse implements Serializable {

    @ApiModelProperty(value = "Статус заявки")
    private ApplicationStatus status;
}
