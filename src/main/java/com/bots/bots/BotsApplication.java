package com.bots.bots;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.bots.bots.config.datasource.DynamicDataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * 机器人应用程序
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/01
 */
@Import({DynamicDataSourceConfig.class})
@EnableConfigurationProperties
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class BotsApplication {
    /**
     * 主要
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(BotsApplication.class, args);
    }

}
