package com.example.rdsreadonlytest.config;

import com.example.rdsreadonlytest.common.datasource.RoutingDataSourceResolver;
import com.example.rdsreadonlytest.domain.enums.DataSourceType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public JdbcTemplate createJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "secondaryjdbc")
    public JdbcTemplate createSecondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    RoutingDataSourceResolver routingDataSourceResolver(DataSource dataSource, @Qualifier("secondaryDataSource") DataSource secondary) {

        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DataSourceType.Updatable, dataSource);
        targetDataSources.put(DataSourceType.ReadOnly, secondary);

        // データソースの動的変更
        RoutingDataSourceResolver resolver = new RoutingDataSourceResolver();
        resolver.setTargetDataSources(targetDataSources);
        resolver.setDefaultTargetDataSource(dataSource);

        return resolver;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(RoutingDataSourceResolver resolver) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(resolver);
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();

        // MyBatisの設定を記述

        return sqlSessionFactory;
    }

}