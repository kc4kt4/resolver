package ru.kc4kt4.resolver;

import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class FlywayConfigurationTest {

    @Bean("dataSourceTest")
    @Primary
    public javax.sql.DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    @Bean(initMethod = "migrate")
    @DependsOn("dataSourceTest")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure().dataSource(dataSource)
                .locations("migration").load();
    }
}
