package com.bots.bots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 机器人应用程序
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/01
 */
@EnableConfigurationProperties
@SpringBootApplication()
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
