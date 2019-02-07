package ru.kc4kt4.resolver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.kc4kt4.resolver.dto.ApplicationDTO;
import ru.kc4kt4.resolver.enums.ApplicationStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessfulResponse {

    private ApplicationDTO application;
    private ApplicationStatus status;
}
