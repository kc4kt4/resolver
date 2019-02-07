package ru.kc4kt4.resolver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * The type Application dto.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = IndividualDTO.class, name = "INDIVIDUAL"),
        @JsonSubTypes.Type(value = CompanyDTO.class, name = "COMPANY")
})
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDTO implements Serializable {

    private Long id;
    private String type;
}
