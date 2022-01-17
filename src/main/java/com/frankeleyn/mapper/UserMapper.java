package com.frankeleyn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.frankeleyn.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Frankeleyn
 * @date 2022/1/17 15:26
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> findUsersByName(String name);

    IPage<User> findUserPageByName(IPage<User> page, @Param("name") String name);
}
