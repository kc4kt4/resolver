package ru.kc4kt4.resolver.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Valid
public class IndividualDTO extends ApplicationDTO {

    @ApiModelProperty(value = "Name",
            required = true,
            example = "Anna")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "Surname",
            required = true,
            example = "Bergen")
    @NotBlank
    private String surname;

    @ApiModelProperty(value = "Patronymic",
            example = "+79998881111")
    @Pattern(regexp = "^\\+[0-9]{11}$")
    @NotBlank
    private String phone;
}

