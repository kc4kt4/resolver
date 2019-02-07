package ru.kc4kt4.resolver.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * The type Company dto.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CompanyDTO extends ApplicationDTO {

    @NotBlank
    private String directorName;

    @NotBlank
    private String directorSurname;

    @NotBlank
    private String companyName;
}
