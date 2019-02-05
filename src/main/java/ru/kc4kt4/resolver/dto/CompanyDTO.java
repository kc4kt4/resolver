package ru.kc4kt4.resolver.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Valid
public class CompanyDTO extends ApplicationDTO {

    @ApiModelProperty(value = "Имя директора компании",
            required = true,
            example = "Peter")
    @NotBlank
    private String directorName;

    @ApiModelProperty(value = "Фамилия директора компании",
            required = true,
            example = "Alonso")
    @NotBlank
    private String directorSurname;

    @ApiModelProperty(value = "Наименование компании",
            required = true,
            example = "PepsiCO")
    @NotBlank
    private String companyName;
}
