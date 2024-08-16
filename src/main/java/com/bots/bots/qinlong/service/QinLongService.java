package com.bots.bots.qinlong.service;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.bots.bots.qinlong.exception.QinLongException;
import com.bots.bots.qinlong.pojo.QinLong;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 青龙服务
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/16
 */
@Service
@Slf4j
public class QinLongService {
    /**
     * Web 客户端
     */
    private final WebClient webClient = WebClient.builder().build();
    /**
     * 青龙
     */
    @Resource
    private QinLong qinLong;

    /**
     * 获得授权
     *
     * @return {@link String }
     */
    private String getAuthorization() {
        AtomicReference<String> token = new AtomicReference<>("");
        String requestBody = ("{\n" +
                "  \"username\": \"" + qinLong.getUserName() + "\",\n" +
                "  \"password\": \"" + qinLong.getPassWord() + "\"\n" +
                "}");
        try {
            HashMap<String, Object> block = webClient.post()
                    .uri(qinLong.getUrl() + "/api/user/login?t=" + new Date().getTime())
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

    /**
     * 更新
     *
     * @param value 值
     */
    public String updateJD(String value) {
        String authorization = getAuthorization();
        if (StringUtils.isNotBlank(authorization)) {
            return Optional.ofNullable(webClient.get()
                    .uri(qinLong.getUrl() + "api/envs?searchValue=" + qinLong.getVariable() + "&t=" + new Date().getTime())
                    .header("Content-Type", "application/json")
                    .header("authorization", "Bearer " + authorization)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<HashMap<String, Object>>() {
                    })
                    .block()).map(queryEnvironmentVariables -> {
                if (Objects.equals(200, queryEnvironmentVariables.get("code"))) {
                    Object data = queryEnvironmentVariables.get("data");
                    List<HashMap<String, Object>> dataList = JSONObject.parseObject(data.toString(), new TypeReference<List<HashMap<String, Object>>>() {
                    });
                    return dataList.get(0).get("id");
                } else return Optional.empty();
            }).map(id -> {
                String modify = "{\n" +
                        "  \"name\": \"" + qinLong.getVariable() + "\",\n" +
                        "  \"value\": \"" + value.trim() + "\",\n" +
                        "  \"remarks\": null,\n" +
                        "  \"id\": " + id + "\n" +
                        "}";
                return Optional.ofNullable(webClient.put()
                        .uri(qinLong.getUrl() + "api/envs?t=" + new Date().getTime())
                        .header("Content-Type", "application/json")
                        .header("authorization", "Bearer " + authorization)
                        .bodyValue(modify)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<HashMap<String, Object>>() {
                        })
                        .block()).map(block -> {
                    if (ObjectUtils.isNotEmpty(block) && !Objects.equals(200, block.get("code"))) {
                        return String.valueOf(block.get("data"));
                    } else {
                        return "修改成功";
                    }
                }).orElseThrow(() -> new QinLongException("调用修改失败"));
            }).orElseThrow(() -> new QinLongException("调用查询失败"));
        } else {
            throw new QinLongException("获取请求校验失败");
        }
    }
}
