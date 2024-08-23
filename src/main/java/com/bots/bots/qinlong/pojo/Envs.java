package com.bots.bots.qinlong.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Size;

/**
 * envs
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/16
 */
@Slf4j
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@TableName("Envs")
public class Envs {
    /**
     * ID
     */
    private Long id;

    /**
     * 值
     */
    @Size(max = 255, message = "值长度不能超过255")
    private String value;

    /**
     * 时间戳
     */
    @Size(max = 255, message = "时间戳长度不能超过255")
    private String timestamp;

    /**
     * 地位
     */
    private Integer status;

    /**
     * 位置
     */
    private Long position;

    /**
     * 名称
     */
    @Size(max = 255, message = "名称长度不能超过255")
    private String name;

    /**
     * 备注
     */
    @Size(max = 255, message = "备注长度不能超过255")
    private String remarks;
}
