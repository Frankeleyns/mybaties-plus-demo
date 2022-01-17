package com.frankeleyn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frankeleyn.entity.User;
import com.frankeleyn.mapper.UserMapper;
import com.frankeleyn.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Frankeleyn
 * @date 2022/1/17 10:16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
