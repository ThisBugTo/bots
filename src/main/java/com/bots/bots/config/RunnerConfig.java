package com.bots.bots.config;

import com.bots.bots.pojo.MyTelegramBot;
import com.bots.bots.utils.ConnectUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * 跑步者配置
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/01
 */
@Data
@Slf4j
@Component
public class RunnerConfig implements CommandLineRunner {
    /**
     * 代理主机
     */
    @Value("${proxy.host}")
    private String proxyHost;
    /**
     * 代理端口
     */
    @Value("${proxy.port}")
    private String proxyPort;
    /**
     * 代理类型
     */
    @Value("${proxy.type}")
    private String proxyType;
    /**
     * 机器人用户名
     */
    @Value("${bot.username}")
    private String botUsername;
    /**
     * 机器人令牌
     */
    @Value("${bot.token}")
    private String botToken;
    /**
     * 青龙登陆名称
     */
    public static String qingLongUserName;
    /**
     * 青龙登陆密码
     */
    public static String qingLongPassWord;
    /**
     * 青龙地址
     */
    public static String qingLongUrl;
    /**
     * 青龙id
     */
    public static String qingLongId;
    /**
     * 设置青龙登陆名称
     *
     * @param qingLongUserName 青龙登陆名称
     */
    @Value("${qing-long.username}")
    public void setQingLongUserName(String qingLongUserName) {
        RunnerConfig.qingLongUserName = qingLongUserName;
    }
    /**
     * 设置青龙登陆密码
     *
     * @param qingLongPassWord 青龙登陆密码
     */
    @Value("${qing-long.password}")
    public void setQingLongPassWord(String qingLongPassWord) {
        RunnerConfig.qingLongPassWord = qingLongPassWord;
    }
    /**
     * 设置青龙地址
     *
     * @param qingLongUrl 青龙地址
     */
    @Value("${qing-long.url}")
    public void setQingLongUrl(String qingLongUrl) {
        RunnerConfig.qingLongUrl = qingLongUrl;
    }
    /**
     * 设置青龙id
     *
     * @param qingLongId 庆隆id
     */
    @Value("${qing-long.id}")
    public void setQingLongId(String qingLongId) {
        RunnerConfig.qingLongId = qingLongId;
    }


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
            botOptions.setProxyHost(proxyHost);
            botOptions.setProxyPort(Integer.parseInt(proxyPort));
            botOptions.setProxyType(DefaultBotOptions.ProxyType.valueOf(proxyType));
        }
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new MyTelegramBot(botUsername, botToken, botOptions));
        log.info("连接成功");
    }
}
