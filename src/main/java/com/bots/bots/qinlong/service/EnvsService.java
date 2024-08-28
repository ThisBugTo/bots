package com.bots.bots.qinlong.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bots.bots.qinlong.mapper.EnvsMapper;
import com.bots.bots.qinlong.pojo.Envs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * envs服务
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/16
 */
@Service
@Slf4j
public class EnvsService {
    /**
     * envs映射器
     */
    @Resource
    private EnvsMapper envsMapper;

    /**
     * 更新JD
     *
     * @param value 值
     * @return boolean
     */
    public boolean updateJD(String value) {
        return Optional.ofNullable(envsMapper.selectOne(new LambdaQueryWrapper<Envs>().eq(Envs::getName, "JD_COOKIE")))
                .map(envs -> envsMapper.updateById(envs.setValue(value).setStatus(0)) > 0).orElse(false);
    }
}
