package com.topov.estatesearcher.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component(value = "dataSourceProperties")
public class DataSourceProperties {
    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;

    public DataSourceProperties(@Value("${datasource.url}") String url,
                                @Value("${datasource.username}") String username,
                                @Value("${datasource.password}") String password,
                                @Value("${datasource.driver-class-name}") String driverClassName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }
}
