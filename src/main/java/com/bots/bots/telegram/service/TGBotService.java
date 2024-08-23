package com.bots.bots.telegram.service;

import com.bots.bots.qinlong.exception.QinLongException;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * tg机器人服务
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/16
 */
@Slf4j
public class TGBotService extends TelegramLongPollingBot {
    /**
     * 机器人用户名
     */
    private final String botUsername;
    /**
     * 机器人令牌
     */
    private final String botToken;

    /**
     * 获取机器人用户名
     *
     * @return {@link String }
     */
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    /**
     * 获取机器人令牌
     *
     * @return {@link String }
     */
    @Override
    public String getBotToken() {
        return botToken;
    }

    /**
     * 我的电报机器人
     *
     * @param botUsername 机器人用户名
     * @param botToken    机器人令牌
     * @param options     选项
     */
    public TGBotService(String botUsername, String botToken, DefaultBotOptions options) {
        super(options);
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    /**
     * 收到更新后
     *
     * @param update 更新
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            log.info("收到消息：{}", messageText);
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            //todo 考虑直接调用京东参数
            if (messageText.contains("pt_key") && messageText.contains("pt_pin")) {
                try {
                    if (!ServiceStatic.envsService.updateJD(messageText)) {
                        message.setText(ServiceStatic.qinLongService.updateJD(messageText));
                    } else {
                        message.setText("修改成功");
                    }
                } catch (QinLongException qinLongException) {
                    message.setText("调用青龙失败: " + messageText);
                }
            } else {
                message.setText("接收到: " + messageText);
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error("执行错误:{}", e.getMessage());
            }
        }
    }
}
