package com.frankeleyn;

import com.frankeleyn.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Frankeleyn
 * @date 2022/1/15 10:42
 */
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindAll() {
        userMapper.selectList(null).forEach(System.out::println);
    }
}
