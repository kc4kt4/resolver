package ru.kc4kt4.resolver.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.kc4kt4.resolver.util.Constants;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Valid
public class IndividualDTO extends ApplicationDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Pattern(regexp = Constants.PHONE)
    @NotBlank
    private String phone;
}

