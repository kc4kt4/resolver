package ru.kc4kt4.resolver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.kc4kt4.resolver.enums.ApplicationStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse extends AbstractResponse {

    @ApiModelProperty(value = "Описание ошибки")
    private String message;

    public ErrorResponse(String message) {
        super.setStatus(ApplicationStatus.REJECTED);
        this.message = message;
    }
}
