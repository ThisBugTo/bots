package com.bots.bots.telegram.config;

import com.bots.bots.pojo.Proxy;
import com.bots.bots.telegram.pojo.TGBot;
import com.bots.bots.telegram.service.TGBotService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.Resource;

/**
 * 跑步者配置
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/01
 */
@Slf4j
@Component
public class RunnerConfig implements CommandLineRunner {
    /**
     * 代理人
     */
    @Resource
    private Proxy proxy;
    /**
     * TG机器人
     */
    @Resource
    private TGBot tgBot;

    /**
     * 是使用代理
     */
    private static boolean isUseProxy = false;
    /**
     * 默认机器人选项
     */
    private static final DefaultBotOptions defaultBotOptions = new DefaultBotOptions();

    /**
     * 运行
     *
     * @param args args
     */
    @Override
    public void run(String... args) {
        connect();
    }

    /**
     * 连接
     */
    private void connect() {
        if (isUseProxy) {
            throw new RuntimeException("代理未生效,请检查配置");
        }
        if (ObjectUtils.isNotEmpty(defaultBotOptions.getProxyHost())) {
            isUseProxy = true;
        }
        String baseUrl = defaultBotOptions.getBaseUrl();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TGBotService(tgBot.getUserName(), tgBot.getToken(), defaultBotOptions, tgBot.getRootId()));
            log.info("连接成功");
        } catch (TelegramApiException telegramApiException) {
            log.debug("连接[{}]失败,开始使用代理", baseUrl);
            defaultBotOptions.setProxyHost(proxy.getHost());
            defaultBotOptions.setProxyPort(Integer.parseInt(proxy.getPort()));
            defaultBotOptions.setProxyType(DefaultBotOptions.ProxyType.valueOf(proxy.getType()));
            connect();
        } catch (Exception e) {
            log.error("连接[{}]发生异常:[{}]", baseUrl, e.getMessage());
        }
    }
}
