package com.bots.bots.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源配置
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/16
 */
@Configuration
@Slf4j
public class DynamicDataSourceConfig {
    /**
     * SQL Lite数据源
     *
     * @return {@link DataSource }
     */
    @Bean("sqLiteDataSource")
    @ConfigurationProperties("spring.datasource.druid.sqlite")
    public DataSource sqLiteDataSource() {
        DruidDataSource build = DruidDataSourceBuilder.create().build();
        build.setUrl("jdbc:sqlite:C:/Users/Other/Downloads/Edge/database.sqlite");
        build.setDriverClassName("org.sqlite.JDBC");
        return build;
    }

    /**
     * 数据源
     *
     * @param sqLiteDataSource 平方米数据源
     * @return {@link DynamicDataSource }
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource sqLiteDataSource) {
        log.debug("开始配置动态数据源");
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.SQLITE, sqLiteDataSource);
        return new DynamicDataSource(sqLiteDataSource, targetDataSources);
    }

}
