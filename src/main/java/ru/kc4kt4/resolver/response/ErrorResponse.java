package ru.kc4kt4.resolver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.kc4kt4.resolver.enums.ApplicationStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse extends AbstractResponse {

    private String message;

    public ErrorResponse(String message) {
        super.setStatus(ApplicationStatus.NOT_READY_OR_REJECTED);
        this.message = message;
    }
}
