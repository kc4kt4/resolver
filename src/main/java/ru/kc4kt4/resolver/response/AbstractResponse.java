package ru.kc4kt4.resolver.response;

import lombok.Data;
import ru.kc4kt4.resolver.enums.ApplicationStatus;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Data
public abstract class AbstractResponse implements Serializable {

    private ApplicationStatus status;
}
