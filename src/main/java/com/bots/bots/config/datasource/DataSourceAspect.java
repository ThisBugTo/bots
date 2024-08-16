package com.bots.bots.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据源特征
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/16
 */
@Aspect
@Component
@Slf4j
public class DataSourceAspect implements Ordered {
    /**
     * 获取订单
     *
     * @return int
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 数据源切入点
     */
    @Pointcut("@annotation(com.bots.bots.config.datasource.DataSource)")
    public void dataSourcePointCut() {
    }

    /**
     * 周围
     *
     * @param point 点
     * @return {@link Object }
     * @throws Throwable Throwable
     */
    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSource ds = method.getAnnotation(DataSource.class);
        // 通过判断 DataSource 中的值来判断当前方法应用哪个数据源
        DynamicDataSource.setDataSource(ds.value());
        log.debug("设置数据源为:{}", ds.value());
        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            log.debug("清除数据源");
        }
    }

}
