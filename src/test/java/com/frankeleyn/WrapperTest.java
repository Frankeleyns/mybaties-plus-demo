package com.frankeleyn;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.frankeleyn.entity.User;
import com.frankeleyn.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author Frankeleyn
 * @date 2022/1/18 10:21
 */
@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testWrapper10() {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(User::getAge, 20)
                .set(User::getEmail, "user@atguigu.com")
                .like(User::getName, "n")
                .and(i -> i.lt(User::getAge, 20).or().isNull(User::getEmail));

        int row = userMapper.update(new User(), updateWrapper);
        System.out.println(row + " 行被修改");
    }

    @Test
    public void testWrapper09() {
        //定义查询条件，有可能为null（用户未输入）
        String name = null;
        Integer ageBegin = 20;
        Integer ageEnd = 30;

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), User::getName, "n")
                    .gt(ageBegin != null, User::getAge, ageBegin)
                    .lt(ageEnd != null, User::getAge, ageEnd);

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testWrapper08() {
        //定义查询条件，有可能为null（用户未输入）
        String name = null;
        Integer ageBegin = 20;
        Integer ageEnd = 30;

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                    .gt(ageBegin != null, "age", ageBegin)
                    .lt(ageEnd != null, "age", ageEnd);

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    // 查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 user@github.com
    @Test
    public void testWrapper07() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("age", 18)
                     .set("email", "user@github.com")
                     .like("name", "n")
                     .and(i -> i.lt("age", 18).or().isNull("email"));

        //这里必须要创建User对象，否则无法应用自动填充。如果没有自动填充，可以设置为null
        User user = new User();
        int row = userMapper.update(user, updateWrapper);
        System.out.println(row + " 行被修改");
    }

    // 查询 id 不大于3的所有用户的 id 列表
    @Test
    public void testWrapper06() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id", "select id from user where id < 3");
/*        queryWrapper.lt("id", 3);
        queryWrapper.in("id", 1,2,3);*/
        List<Object> objects = userMapper.selectObjs(queryWrapper);
        objects.forEach(System.out::println);
    }

    // 查询所有用户的用户名和年龄
    @Test
    public void testWrapper05() {
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "age");

        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    // 查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 user@github.com
    @Test
    public void testWrapper04() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "n")
                    .and(i -> i.lt("age", 18).or().isNull("email"));

        User user = new User();
        user.setAge(18);
        user.setEmail("user@github.com");

        int row = userMapper.update(user, queryWrapper);
        System.out.println(row + " 行被修改");
    }

    // 删除 email 为空的所有用户
    @Test
    public void testWrapper03() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");

        int row = userMapper.delete(queryWrapper);
        System.out.println("删除 " + row + " 条数据");
    }

    // 按年龄降序查询用户，如果年龄相同则按id升序排列
    @Test
    public void testWrapper02() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .orderByDesc("age")
                .orderByAsc("id");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    // 查询名字中包含 n，年龄大于等于20且小于等于30，email 不为空的用户
    @Test
    public void testWrapper01() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("name","n")
                .between("age", 20, 30)
                .isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
