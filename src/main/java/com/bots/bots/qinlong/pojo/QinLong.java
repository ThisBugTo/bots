package com.bots.bots.qinlong.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "qin-long")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QinLong {
    /**
     * 青龙登陆名称
     */
    private String userName;
    /**
     * 青龙登陆密码
     */
    private String passWord;
    /**
     * 青龙地址
     */
    private String url;
    /**
     * 环境变量
     */
    private String variable;
}
