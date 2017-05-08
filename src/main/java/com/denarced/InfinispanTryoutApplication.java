package com.denarced;

import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@SpringBootApplication
public class InfinispanTryoutApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfinispanTryoutApplication.class, args);
    }

    @Bean
    public FluentJdbc fluentJdbc(@Autowired DataSource dataSource) {
        return new FluentJdbcBuilder()
            .connectionProvider(dataSource)
            .build();
    }
}
