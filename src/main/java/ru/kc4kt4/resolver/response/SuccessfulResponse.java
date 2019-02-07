package ru.kc4kt4.resolver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.kc4kt4.resolver.dto.ApplicationDTO;

/**
 * The type Successful response.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessfulResponse {

    private ApplicationDTO application;
}
