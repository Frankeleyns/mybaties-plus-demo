package com.frankeleyn.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Frankeleyn
 * @date 2022/1/14 16:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 名字
    private String name;

    // 年龄
    private Integer age;

    // 邮箱
    private String email;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
