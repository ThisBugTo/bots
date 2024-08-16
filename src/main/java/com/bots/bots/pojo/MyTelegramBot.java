package com.bots.bots.pojo;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.bots.bots.config.RunnerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 我的电报机器人
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/01
 */
@Slf4j
public class MyTelegramBot extends TelegramLongPollingBot {
    /**
     * 机器人用户名
     */
    private final String botUsername;
    /**
     * 机器人令牌
     */
    private final String botToken;
    /**
     * Web 客户端
     */
    private final WebClient webClient = WebClient.builder().build();
    /**
     * URL
     */
    private final String url = RunnerConfig.qingLongUrl;

    /**
     * 我的电报机器人
     *
     * @param botUsername 机器人用户名
     * @param botToken    机器人令牌
     * @param options     选项
     */
    public MyTelegramBot(String botUsername, String botToken, DefaultBotOptions options) {
        super(options);
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

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
                message.setText(update(messageText));
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

    /**
     * 更新
     *
     * @param value 值
     */
    private String update(String value) {
        String authorization = getAuthorization();
        if (StringUtils.isNotBlank(authorization)) {
            String requestBody = "{\n" +
                    "  \"name\": \"JD_COOKIE\",\n" +
                    "  \"value\": \"" + value.trim() + "\",\n" +
                    "  \"remarks\": null,\n" +
                    "  \"id\": " + RunnerConfig.qingLongId + "\n" +
                    "}";
            try {
                HashMap<String, Object> block = webClient.put()
                        .uri(url + "api/envs?t=" + new Date().getTime())
                        .header("Content-Type", "application/json")
                        .header("authorization", "Bearer " + authorization)
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<HashMap<String, Object>>() {
                        })
                        .block();
                if (ObjectUtils.isNotEmpty(block) && !Objects.equals(200, block.get("code"))) {
                    log.info("调用修改返回失败信息：{}", block);
                    return String.valueOf(block.get("data"));
                }
                log.info("调用修改返回成功信息：{}", block);
            } catch (Exception e) {
                log.error("调用修改异常：{}", e.getMessage());
                return "修改失败";
            }
        }
        return "修改成功";
    }

    /**
     * 获得授权
     *
     * @return {@link String }
     */
    private String getAuthorization() {
        AtomicReference<String> token = new AtomicReference<>("");
        String requestBody = ("{\n" +
                "  \"username\": \"" + RunnerConfig.qingLongUserName + "\",\n" +
                "  \"password\": \"" + RunnerConfig.qingLongPassWord + "\"\n" +
                "}");
        try {
            HashMap<String, Object> block = webClient.post()
                    .uri(url + "/api/user/login?t=" + new Date().getTime())
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<HashMap<String, Object>>() {
                    })
                    .block();
            if (ObjectUtils.isNotEmpty(block) && Objects.equals(200, block.get("code"))) {
                log.info("调用登陆返回信息:{}", block);
                Optional.ofNullable(block.get("data")).ifPresent(data -> {
                    HashMap<String, Object> stringObjectHashMap = JSONObject.parseObject(JSONObject.toJSONString(data), new TypeReference<HashMap<String, Object>>() {
                    });
                    token.set(String.valueOf(stringObjectHashMap.get("token")));
                });
            }
        } catch (Exception e) {
            log.error("调用登陆失败");
        }
        return token.get();
    }
}
