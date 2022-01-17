package com.frankeleyn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frankeleyn.entity.User;

import java.util.List;

/**
 * @author Frankeleyn
 * @date 2022/1/14 16:33
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> findUsersByName(String name);
}
