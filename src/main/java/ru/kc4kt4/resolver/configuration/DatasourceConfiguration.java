package ru.kc4kt4.resolver.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.hql.spi.id.local.LocalTemporaryTableBulkIdStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.kc4kt4.resolver.configuration.properties.DataSourceProperties;
import ru.kc4kt4.resolver.configuration.properties.HikariProperties;

import javax.sql.DataSource;

import java.util.HashMap;

@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement
public class DatasourceConfiguration {

    private final DataSourceProperties dataSourceProperties;
    private final HikariProperties hikariProperties;

    @Autowired
    public DatasourceConfiguration(DataSourceProperties config,
                                   HikariProperties hikariProperties) {
        this.dataSourceProperties = config;
        this.hikariProperties = hikariProperties;
    }

    @Bean("dataSource")
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(org.postgresql.Driver.class.getName());
        hikariConfig.setJdbcUrl(dataSourceProperties.getDataBaseUrl());
        hikariConfig.setUsername(dataSourceProperties.getUser());
        hikariConfig.setPassword(dataSourceProperties.getPassword());
        hikariConfig.setMinimumIdle(hikariProperties.getMinPoolSize());
        hikariConfig.setMaximumPoolSize(hikariProperties.getMaxPoolSize());
        hikariConfig.setConnectionTestQuery(hikariProperties.getPreferredTestQuery());
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            EntityManagerFactoryBuilder builder) {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put(AvailableSettings.HBM2DDL_AUTO, dataSourceProperties.getDdlAuto());
        properties.put(AvailableSettings.DIALECT, dataSourceProperties.getDialect());
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY,
                       "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put(AvailableSettings.DEFAULT_SCHEMA, dataSourceProperties.getSchema());
        properties.put(AvailableSettings.HQL_BULK_ID_STRATEGY, LocalTemporaryTableBulkIdStrategy.class.getName());

        return builder
                .dataSource(dataSource)
                .packages("ru.kc4kt4.resolver.entity", "org.springframework.documents.jpa.convert.threeten")
                .persistenceUnit("resolver")
                .properties(properties)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emFactory) {
        return new JpaTransactionManager(emFactory.getObject());
    }

    @Bean
    public JdbcTemplate amcJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
