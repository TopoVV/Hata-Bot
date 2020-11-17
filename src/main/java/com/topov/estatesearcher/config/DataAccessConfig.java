package com.topov.estatesearcher.config;

import com.topov.estatesearcher.config.properties.DataSourceProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Log4j2
@Configuration
@PropertySource(value = "classpath:datasource.properties")
public class DataAccessConfig {
    private final DataSourceProperties dataSourceProperties;

    @Autowired
    public DataAccessConfig(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean
    DataSource dataSource() {
        log.info("Instantiating datasource");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(this.dataSourceProperties.getUrl());
        dataSource.setUsername(this.dataSourceProperties.getUsername());
        dataSource.setPassword(this.dataSourceProperties.getPassword());
        dataSource.setDriverClassName(this.dataSourceProperties.getDriverClassName());
        return dataSource;
    }
}
