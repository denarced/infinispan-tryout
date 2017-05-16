package com.denarced;

import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
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

    @Bean
    public Cache<String,String> infinispanCache() {
        GlobalConfigurationBuilder gcb = GlobalConfigurationBuilder.defaultClusteredBuilder();
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder
            .clustering()
            .cacheMode(CacheMode.REPL_SYNC)
            .create();
        DefaultCacheManager manager = new DefaultCacheManager(
            gcb.build(),
            builder.build());
        return manager.getCache();
    }
}
