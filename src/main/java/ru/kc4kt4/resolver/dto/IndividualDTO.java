package ru.kc4kt4.resolver.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.kc4kt4.resolver.util.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * The type Individual dto.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class IndividualDTO extends ApplicationDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Pattern(regexp = Constants.PHONE)
    @NotBlank
    private String phone;
}

