package com.templateTools.pub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name="dataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource getDataSource(){
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.type(CreateCodeDataSource.class);
        return builder.build();
    }

}
