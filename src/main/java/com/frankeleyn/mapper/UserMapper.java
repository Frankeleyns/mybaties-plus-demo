package com.frankeleyn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frankeleyn.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Frankeleyn
 * @date 2022/1/14 16:33
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
