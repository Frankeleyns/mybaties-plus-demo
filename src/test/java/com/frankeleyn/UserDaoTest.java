package com.frankeleyn;

import com.frankeleyn.entity.User;
import com.frankeleyn.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Frankeleyn
 * @date 2022/1/15 10:42
 */
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindUserByName() {
        // 查询用户名字中带有 Obama
        List<User> userList = userMapper.findUsersByName("Obama");
        userList.forEach(System.out::println);
    }

    @Test
    public void testDelete() {
        // 删除 id 为5的用户
        int row = userMapper.deleteById(5);
        System.out.println("影响的行数： " + row);

    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(1482963171146280962L);
        user.setAge(33);
        // 更新 id 为 1的用户年龄为 28
        int row = userMapper.updateById(user);
        System.out.println("影响的行数： " + row);
    }

    @Test
    public void testSelect() {
        // 根据 id 查询用户
        User user1 = userMapper.selectById(1L);
        System.out.println(user1);

        // 根据 id 列表查询多个用户
        List<User> userList = userMapper.selectBatchIds(Arrays.asList(2L, 3L, 4L));
        userList.forEach(System.out::println);

        // 根据 map 中的条件查询，查询名字为 Franklin, 年龄为 21 的用户
        Map map = new HashMap();
        // map 的键使用数据库的字段名，不是类中的属性名
        map.put("name", "Franklin");
        map.put("age", "21");
        List usersList = userMapper.selectByMap(map);
        usersList.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        // 构造器模式创建 User 对象
        User user = User.builder()
                .name("Vincent1")
                .age(33)
                .email("Vincent1@qq.com").build();

        int row = userMapper.insert(user);
        System.out.println("影响的行数：" + row);
        System.out.println("获取自动生成的 id: " + user.getId());
    }

    @Test
    public void testFindAll() {
        userMapper.selectList(null).forEach(System.out::println);
    }

}
