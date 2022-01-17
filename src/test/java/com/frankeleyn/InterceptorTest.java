package com.frankeleyn;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frankeleyn.entity.User;
import com.frankeleyn.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Frankeleyn
 * @date 2022/1/17 15:25
 */
@SpringBootTest
public class InterceptorTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testDivPage() {
        Page<User> page = new Page<>();
        page.setCurrent(2); // 当前页数
        page.setSize(2); // 每页条数

        IPage<User> pages = userMapper.findUserPageByName(page, "Obama");
        pages.getRecords().forEach(System.out::println);
    }

    @Test
    public void testFindByPage() {
        Page<User> page = new Page<>();
        page.setCurrent(2); // 当前页数
        page.setSize(3); // 每页条数

        Page<User> userPage = userMapper.selectPage(page, null);

        long total = userPage.getTotal(); // 总记录数
        System.out.println("总记录数： " + total);
        List<User> records = userPage.getRecords();
        records.forEach(System.out::println);
    }

}
