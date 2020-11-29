package com.topov.hatabot.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Log4j2
@Configuration
@PropertySource(value = "classpath:datasource.properties")
public class DataAccessConfig {
    private final Environment env;

    @Autowired
    public DataAccessConfig(Environment env) {
        this.env = env;
    }

    @Bean
    DataSource dataSource() {
        try {
            log.info("Instantiating the datasource");
            final String url = this.env.getProperty("datasource.url");
            final String username = this.env.getProperty("datasource.username");
            final String password = this.env.getProperty("datasource.password");
            final String driver = this.env.getProperty("datasource.driver-class-name");

            Objects.requireNonNull(driver);

            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setDriverClassName(driver);

            return dataSource;
        } catch (NullPointerException e) {
            log.error("No JDBC driver class name specified", e);
            throw new RuntimeException("Not JDBC driver class name specified", e);
        }
    }
}
