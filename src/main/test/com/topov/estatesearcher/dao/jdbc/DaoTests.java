package com.topov.estatesearcher.dao.jdbc;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DaoTests.DaoTestsContextConfig.class })
public class DaoTests {

    @Configuration
    public static class DaoTestsContextConfig {

        @Bean
        DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl("jdbc:postgresql://localhost:5432/estate_searcher_test_db");
            dataSource.setUsername("topovv");
            dataSource.setPassword("admin");
            dataSource.setDriverClassName("org.postgresql.Driver");
            return dataSource;
        }
    }
}
