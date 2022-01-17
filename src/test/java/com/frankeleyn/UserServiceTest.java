package com.frankeleyn;

import com.frankeleyn.entity.User;
import com.frankeleyn.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

/**
 * @author Frankeleyn
 * @date 2022/1/17 10:19
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testBatchAdd() {
        // 测试批量插入数据
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("Obama" + i);
            user.setAge(10 + i);
            users.add(user);
        }
        userService.saveBatch(users);
    }

    @Test
    public void testAdd() {
        // 获取数据库所有记录数
        int count = userService.count();
        System.out.println(count);
    }

}

