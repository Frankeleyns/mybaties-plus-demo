package com.frankeleyn.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * @author Frankeleyn
 * @date 2022/1/17 17:02
 */
@Data
public class Product {

    private Long id;

    private String name;

    private Integer price;

    @Version
    private Integer version;
}
