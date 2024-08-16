package com.bots.bots.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/16
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 上下文持有者
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 动态数据源
     *
     * @param defaultTargetDataSource 默认目标数据源
     * @param targetDataSources       目标数据源
     */
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    /**
     * 确定当前查找关键字
     *
     * @return {@link Object }
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    /**
     * 设置数据源
     *
     * @param dataSource 数据源
     */
    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    /**
     * 获取数据源
     *
     * @return {@link String }
     */
    public static String getDataSource() {
        return contextHolder.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        contextHolder.remove();
    }
}
