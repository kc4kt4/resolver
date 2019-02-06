package ru.kc4kt4.resolver.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * @author vasilevsky.ii on 06.02.2019
 */
@Configuration
@Profile("test")
public class TestConfiguration {

    @Bean("dataSourceTest")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    @Bean
    public TestRestTemplate testRestTemplate() {
        return new TestRestTemplate();
    }

    @Bean(initMethod = "migrate")
    @DependsOn("dataSourceTest")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure().dataSource(dataSource)
                .locations("migration").load();
    }
}
