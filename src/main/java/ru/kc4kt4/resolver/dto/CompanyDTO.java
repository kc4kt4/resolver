package ru.kc4kt4.resolver.dto;

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

    @NotBlank
    private String directorName;

    @NotBlank
    private String directorSurname;

    @NotBlank
    private String companyName;
}
