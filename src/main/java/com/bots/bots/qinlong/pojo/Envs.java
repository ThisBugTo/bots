package com.bots.bots.qinlong.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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
    private LocalDateTime timestamp;

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

    /**
     * 创建时间
     */
    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @NotNull(message = "修改时间不能为空")
    private LocalDateTime updatedAt;
}
