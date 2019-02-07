package ru.kc4kt4.resolver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kc4kt4.resolver.enums.ApplicationStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private ApplicationStatus status;


    public ErrorResponse(String message) {
        this.status = ApplicationStatus.NOT_READY_OR_REJECTED;
        this.message = message;
    }
}
