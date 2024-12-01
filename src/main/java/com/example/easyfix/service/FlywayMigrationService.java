package com.example.easyfix.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FlywayMigrationService {

    @Value("${spring.datasource.url}")
    String configDatasourceUrl;
    @Value("${spring.datasource.username}")
    String configDatasourceUser;
    @Value("${spring.datasource.password}")
    String configDatasourcePassword;
    @Value("${spring.flyway.primary.location}")
    String primaryMigrationLocation;

    @PostConstruct
    public void migrateFlyway() {

        log.info("Flyway Migration Started");

        Flyway.configure().dataSource(configDatasourceUrl, configDatasourceUser, configDatasourcePassword)
                .locations(primaryMigrationLocation).baselineOnMigrate(true).outOfOrder(true).load().migrate();
        log.info("easy fix Flyway is successfully migrated.");
    }

}
