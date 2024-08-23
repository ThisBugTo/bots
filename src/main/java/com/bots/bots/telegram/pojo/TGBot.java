package com.bots.bots.telegram.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * tg机器人
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/16
 */
@Data
@Component
@ConfigurationProperties(value = "bot.telegram")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TGBot {
    /**
     * 机器人用户名
     */
    private String userName;
    /**
     * 机器人令牌
     */
    private String token;
}
