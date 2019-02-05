package ru.kc4kt4.resolver.configuration.properties;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Data
@Component
@ConfigurationProperties("db")
public class DataSourceProperties {

    @NotBlank
    private String host;
    @Range(max = 65535)
    private Integer port;
    @NotBlank
    private String database;
    @NotBlank
    private String user;
    @NotBlank
    private String password;
    @NotBlank
    private String ddlAuto;
    @NotBlank
    private String  showSql;
    @NotBlank
    private String  schema;

    private String  dialect = org.hibernate.dialect.PostgreSQL95Dialect.class.getName();

    public String getDataBaseUrl() {
        return String.format("jdbc:postgresql://%s:%d/%s",
                             this.getHost(),
                             this.getPort(),
                             this.getDatabase());
    }

}

