package com.bots.bots.telegram.config;

import com.bots.bots.pojo.Proxy;
import com.bots.bots.telegram.pojo.TGBot;
import com.bots.bots.telegram.service.TGBotService;
import com.bots.bots.telegram.utils.ConnectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
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
     * 运行
     *
     * @param args args
     * @throws Exception 例外
     */
    @Override
    public void run(String... args) throws Exception {
        //对应代码
        DefaultBotOptions botOptions = new DefaultBotOptions();
        if (!ConnectUtils.connect()) {
            log.info("连接超时，开始启用代理");
            botOptions.setProxyHost(proxy.getHost());
            botOptions.setProxyPort(Integer.parseInt(proxy.getPort()));
            botOptions.setProxyType(DefaultBotOptions.ProxyType.valueOf(proxy.getType()));
        }
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new TGBotService(tgBot.getUserName(), tgBot.getToken(), botOptions));
        log.info("连接成功");
    }
}
