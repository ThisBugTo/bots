package com.bots.bots.config.datasource;

import java.lang.annotation.*;

/**
 * 数据源
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/16
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    /**
     * 值
     *
     * @return {@link String }
     */
    String value() default DataSourceNames.SQLITE;
}
