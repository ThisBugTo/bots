package com.bots.bots.telegram.service;

import com.bots.bots.qinlong.service.EnvsService;
import com.bots.bots.qinlong.service.QinLongService;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 服务静态
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/19
 */
@Data
@Component
public class ServiceStatic {
    /**
     * envs服务
     */
    public static EnvsService envsService;
    /**
     * 青龙服务
     */
    public static QinLongService qinLongService;

    /**
     * 获得envs服务
     *
     * @param envsService envs服务
     */
    @Resource
    private void setEnvsService(EnvsService envsService) {
        ServiceStatic.envsService = envsService;
    }

    /**
     * 得到青龙服务
     *
     * @param qinLongService 秦长服务
     */
    @Resource
    private void setQinLongService(QinLongService qinLongService) {
        ServiceStatic.qinLongService = qinLongService;
    }
}
