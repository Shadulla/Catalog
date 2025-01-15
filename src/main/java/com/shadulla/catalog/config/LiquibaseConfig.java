package com.shadulla.catalog.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class LiquibaseConfig {

    @Value("${spring.liquibase.enabled:true}")
    private boolean liquibaseEnabled;

    @Value("${spring.liquibase.change-log:db/changelog-master.xml}")
    private String liquibaseChangeLog;

    @Value("${spring.liquibase.contexts}")
    private String liquibaseContexts;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(liquibaseChangeLog);
        liquibase.setDataSource(dataSource);
        liquibase.setContexts(liquibaseContexts);
        liquibase.setShouldRun(liquibaseEnabled);
        return liquibase;
    }
}
