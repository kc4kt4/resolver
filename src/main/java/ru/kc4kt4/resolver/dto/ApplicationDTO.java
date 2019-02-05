package ru.kc4kt4.resolver.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IndividualDTO.class, name = "individual"),
        @JsonSubTypes.Type(value = CompanyDTO.class, name = "company")
})
@Data
public class ApplicationDTO {

    @ApiModelProperty("id заявки")
    private Long id;
}
