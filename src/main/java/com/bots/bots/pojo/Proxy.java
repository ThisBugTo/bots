package com.bots.bots.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 代理人
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/16
 */
@Data
@Component
@ConfigurationProperties(value = "proxy")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Proxy {
    /**
     * 代理主机
     */
    private String host;
    /**
     * 代理端口
     */
    private String port;
    /**
     * 代理类型
     */
    private String type;
}
