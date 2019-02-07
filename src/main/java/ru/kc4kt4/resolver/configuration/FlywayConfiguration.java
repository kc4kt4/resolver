package ru.kc4kt4.resolver.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * The type Flyway configuration.
 */
@Configuration
@Profile("!test")
public class FlywayConfiguration {

    /**
     * Flyway flyway.
     *
     * @param dataSource the data source
     * @return the flyway
     */
    @Bean(initMethod = "migrate")
    @DependsOn("dataSource")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("migration")
                .load();
    }
}
