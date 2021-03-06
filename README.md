# MybatisPlus Demo



## 简介

[MyBatis-Plus ](https://baomidou.com/)（简称 MP）是一个 MyBatis 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

![banner](./img/banner.png)



## 入门案例



### 1. 创建数据表 User

```mysql
CREATE TABLE user
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (id)
);
INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```



### 2. 创建 Spring Boot 工程

![02](./img/1-2-1.png)



![03](./img/1-2-2.png)



### 3. 引入依赖

修改你的 pom 文件

```xml
    <!-- 配置 Spring Boot 版本 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.4.RELEASE</version>
    </parent>

    <dependencies>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.1</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        
    </dependencies>
```



### 4. 修改配置文件

![4-1](./img/1-4-1.png)

```properties
# 端口号
server.port= 8082

# ==================== 数据源设置 =========================
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456

# ================ mybatis 配置 =========================
# 驼峰命名
mybatis-plus.configuration.map-underscore-to-camel-case=true
# 扫描映射配置文件
mybatis-plus.mapper-locations=classpath:mapper/*Mapper.xml
```



### 5. 创建实体类

创建包 **entity** 并创建 **User** 实体类

```java
package com.frankeleyn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Frankeleyn
 * @date 2022/1/14 16:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    // 主键
    private Long id;

    // 名字
    private String name;

    // 年龄
    private Integer age;

    // 邮箱
    private String email;

}
```



### 6. 创建 Mapper 接口

创建包 **mapper** 并创建 **UserMapper** 接口继承 **BaseMapper** 接口

```java
package com.frankeleyn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frankeleyn.entity.User;

/**
 * @author Frankeleyn
 * @date 2022/1/14 16:33
 */
public interface UserMapper extends BaseMapper<User> {
    
}
```



### 7. 添加启动类注解

给启动类添加注解 **@MapperScan** ，要不然会报一个找不到 Mapper 的错误

```java
package com.frankeleyn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.frankeleyn.mapper")
public class MybatisPlusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusDemoApplication.class, args);
    }

}
```



### 8. 添加测试类进行功能测试

```java
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
```

打印结果：

![8-1](./img/1-8-1.png)



## 基本用法



### 一、通用 Mapper

在 **Mybatis Plus** 框架中不需要实现 xml 映射配置文件和对应的 sql，它默认有单表的增删改查，都已经在 **BaseMapper\<T>** 中实现了，我们只需要声明一个 Mapper 接口，并继承它就能使用，就像上文入门案例的 [**UserMapper**](##6. 创建 Mapper 接口) 一样。



#### 1. Insert

```java
@Test
public void testInsert() {
    // 构造器模式创建 User 对象
    User user = User.builder()
        .name("Vincent")
        .age(33)
        .email("Vincent@qq.com").build();

    int row = userMapper.insert(user);
    System.out.println("影响的行数：" + row);
    System.out.println("获取自动生成的 id: " + user.getId());
}
```



#### 2. Selete

```java
@Test
public void testSelect() {
    // 根据 id 查询用户
    User user1 = userMapper.selectById(1L);
    System.out.println(user1);

    // 根据 id 列表查询多个用户
    List<User> userList = userMapper.selectBatchIds(Arrays.asList(2L, 3L, 4L));
    userList.forEach(System.out::println);

    // 根据 map 中的条件查询
    Map map = new HashMap();
    // map 的键使用数据库的字段名，不是类中的属性名
    map.put("name", "Franklin");
    map.put("age", "21");
    List usersList = userMapper.selectByMap(map);
    usersList.forEach(System.out::println);
}
```



#### 3. Update

```java
@Test
public void testUpdate() {
    User user = userMapper.selectById(1L);
    user.setAge(28);

    // 更新 id 为 1的用户年龄为 28
    int row = userMapper.updateById(user);
    System.out.println("影响的行数： " + row);
}
```



#### 4. Delete

```java
@Test
public void testDelete() {
    // 删除 id 为5的用户
    int row = userMapper.deleteById(5);
    System.out.println("影响的行数： " + row);
}
```



### 二、通用 Service

**mybatis-plus** 提供一个接口 **IService** 和其实现类 **ServiceImpl** ，封装了常见单表的业务层逻辑，也是 CRUD。



#### 1. 创建 Service 接口

```java
package com.frankeleyn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frankeleyn.entity.User;

/**
 * @author Frankeleyn
 * @date 2022/1/17 10:15
 */
public interface UserService extends IService<User> {
    
}
```



#### 2. 创建 Service 实现类

```java
package com.frankeleyn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frankeleyn.entity.User;
import com.frankeleyn.mapper.UserMapper;
import com.frankeleyn.service.UserService;

/**
 * @author Frankeleyn
 * @date 2022/1/17 10:16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
}
```



####  3. 创建测试类

```java
package com.frankeleyn;

import com.frankeleyn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Frankeleyn
 * @date 2022/1/17 10:19
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    
}
```



#### 4. 获取总记录数

```java
@Test
public void testAdd() {
    // 获取数据库所有记录数
    int count = userService.count();
    System.out.println(count);
}
```



#### 5. 批量插入数据

```java
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
```



### 三、自定义 Mapper

如果通用 Mapper 不符号我们的需求，我们也可以像以前使用 Mybatis 一样，自定义接口方法并配置 xml 文件，写 sql 语句。



#### 1. 接口方法

在 UserMapper 中定义一个方法 **findUsersByName**

```java
public interface UserMapper extends BaseMapper<User> {
    List<User> findUsersByName(String name);
}
```



#### 2. 创建配置文件

在 resource 目录下新建 **mapper** 文件夹，创建 **UserMapper.xml**

![2-3-2](./img/2-3-2.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.frankeleyn.mapper.UserMapper">
    <select id="findUsersByName" resultType="com.frankeleyn.entity.User">
        SELECT * FROM user
        WHERE name like "%"#{name}"%"
    </select>
</mapper>
```



#### 3. 测试自定义条件查询

```java
@Test
public void testFindUserByName() {
    // 查询用户名字中带有 Obama 
    List<User> userList = userMapper.findUsersByName("Obama");
    userList.forEach(System.out::println);
}
```



## 常用注解



### 一、 TableName

表名，将实体类绑定到对应的数据库

```java
@TableName("t_user")
public class User{
    
}
```



### 二、TableId

#### type 属性

type 属性用来指定主键策略

- **AUTO**，主键自增，Mysql 主键自增的最小值总是要大于现有数据的最大值

  ```java
  @TableId(type = IdType.AUTO)
  private Long id;
  ```

- **ASSIGN_ID**，使用**雪花算法**生成主键，例: 1482xxxxxxxxxxx

  ```java
  @TableId(type = IdType.ASSIGN_ID)
  private Long id;
  ```

**id 策略的选择**

一般小数据量，使用自增策略，当数据量超过千万级别后，涉及分库分表，需要使用雪花算法。



### 三、TableField

#### 1. value 属性

将数据库字段和实体类对应的属性绑定

将字段 create_time 和 update_time 绑定到实体类的 createTime 和 updateTime 属性上

![3-3-1](./img/3-3-1.png)

```java
@TableField("create_time")
private LocalDateTime createTime;

@TableField("update_time")
private LocalDateTime updateTime;
```



#### 2. 自动填充

对于字段，比如 create_time 和 update_time 一般情况下不用刻意处理，可以将这两个字段的默认值设为 **CURRENT_TIMESTAMP** 以获取当前时间。

MyBatis Plus 提供了自动填充功能，同样可以完成这些字段的赋值工作。

- 添加 **fill** 属性，指定在哪个操作执行时自动填充

  ```java
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  private LocalDateTime createTime;
  
  @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
  ```

- 配置元数据对象处理器

  ```java
  @Component
  public class MyMetaObjectHandler implements MetaObjectHandler {
      @Override
      public void insertFill(MetaObject metaObject) {
          System.out.println("insertFill...");
          this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
          this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
      }
  
      @Override
      public void updateFill(MetaObject metaObject) {
          System.out.println("updateFill...");
          this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
      }
  
  }
  ```

之后测试插入和修改。



### 四、TableLogic

**逻辑删除：**就是将数据中代表删除的字段修改为**删除状态**，但是在数据库中仍然可以看到该数据。

**@TableLogic** 的作用就是在查询或更新的时候自动为 sql 语句添加一段 你标注的属性 = 0 的后缀



#### 1. 创建逻辑删除列

![3-4-1](./img/3-4-1.png)



#### 2. 实体类添加注解

```java
@TableLogic
@TableField(value = "is_deleted")
private Integer deleted;
```



## 插件



### 一、分页插件

#### 1. 添加配置类

```java
package com.frankeleyn.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Frankeleyn
 * @date 2022/1/17 15:22
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 将分页插件放入容器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
```



#### 2. 测试分页

新建一个测试类 **InterceptorTest**

```java
@SpringBootTest
public class InterceptorTest {

    @Autowired
    private UserMapper userMapper;

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
```



### 二、自定义分页



#### 1. 定义接口方法

```java
public interface UserMapper extends BaseMapper<User> {
    IPage<User> findUserPageByName(IPage<User> page, @Param("name") String name);
}
```



#### 2. 定义 XML

```xml
<select id="findUserPageByName" resultType="com.frankeleyn.entity.User">
    SELECT * FROM user
    WHERE name like "%"#{name}"%"
</select>
```



#### 3. 测试

```java
@Test
public void testDivPage() {
    Page<User> page = new Page<>();
    page.setCurrent(2); // 当前页数
    page.setSize(2); // 每页条数

    IPage<User> pages = userMapper.findUserPageByName(page, "Obama");
    pages.getRecords().forEach(System.out::println);
}
```



#### 提别提醒

如果你用了自定义方法，**@TableLogic** 就不生效了，需要自己手动加你的逻辑删除语句。



### 三、乐观锁



#### 1. 场景

一件商品，成本价是80元，售价是100元。老板先是通知小李，说你去把商品价格增加50元。小李正在玩游戏，耽搁了一个小时。正好一个小时后，老板觉得商品价格增加到150元，价格太高，可能会影响销量。又通知小王，你把商品价格降低30元。

此时，小李和小王同时操作商品后台系统。小李操作的时候，系统先取出商品价格100元；小王也在操作，取出的商品价格也是100元。小李将价格加了50元，并将100+50=150元存入了数据库；小王将商品减了30元，并将100-30=70元存入了数据库。是的，如果没有锁，小李的操作就完全被小王的覆盖了。

- 创建商品表

```java
CREATE TABLE product
(
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '商品名称',
    price INT(11) DEFAULT 0 COMMENT '价格',
    version INT(11) DEFAULT 0 COMMENT '乐观锁版本号',
    PRIMARY KEY (id)
);

INSERT INTO product (id, NAME, price) VALUES (1, '笔记本', 100);
```

- 创建实体类

  ```java
  @Data
  public class Product {
  
      private Long id;
  
      private String name;
  
      private Integer price;
  
      private Integer version;
  }
  ```

- 创建 Mapper

  ```java
  public interface ProductMapper extends BaseMapper<Product> {
      
  }
  ```

- 测试

  ```java
  @Test
  public void testLocal() {
      // 小李
      Product li = productMapper.selectById(1L);
      // 小王
      Product wang = productMapper.selectById(1L);
  
      // 小李将价格提高50元存入数据库
      li.setPrice(li.getPrice() + 50);
      productMapper.updateById(li);
  
      // 小王将价格降低30元存入数据库
      wang.setPrice(wang.getPrice() - 30);
      productMapper.updateById(wang);
  
      // 查询结果
      Product product = productMapper.selectById(1L);
      System.out.println("最后的结果：" + product.getPrice()); // 最后的结果：70
  
  }
  ```



等于 70 是肯定不行的，老板要的售价是 120。



#### 2. Mybati-Plus 中的乐观锁实现

##### ① 修改实体类

给实体类加上 **@Version** 注解

```java
@Version
private Integer version;
```

##### ② 添加乐观锁插件

在 **MybatisPlusConfig** 加入下面这句代码：

```java
// 添加乐观锁插件
interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
```

##### ③ 重新执行测试代码

小王执行会失败，因为 **version** 已经被小李修改了。

##### ④ 优化流程

让小王失败后重试

```java
    @Test
    public void testLocal() {
        // 小李
        Product li = productMapper.selectById(1L);
        // 小王
        Product wang = productMapper.selectById(1L);

        // 小李将价格提高50元存入数据库
        li.setPrice(li.getPrice() + 50);
        productMapper.updateById(li);

        // 小王将价格降低30元存入数据库
        wang.setPrice(wang.getPrice() - 30);
        int row = productMapper.updateById(wang);

        while (row == 0) {
            // 小王执行失败，等待 1 秒后，重新执行
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Product wang2 = productMapper.selectById(1L);
            wang2.setPrice(wang2.getPrice() - 30);
            row = productMapper.updateById(wang2);
        }

        // 查询结果
        Product product = productMapper.selectById(1L);
        System.out.println("最后的结果：" + product.getPrice()); // 最后的结果：120

    }
```





## 条件构造器



### 一、Wrapper 介绍

**Mybatis-Plus** 提供条件构造器来执行较复杂的查询，条件构造器其实就是 **where** 语句的封装。



#### 1. Wrapper 家族

 ![Wrapper家族](./img/5-1-1.png)



Wrapper ： 条件构造抽象类，最顶端父类  

  AbstractWrapper ： 用于查询条件封装，生成 sql 的 where 条件

​    QueryWrapper ： 查询条件封装

​    UpdateWrapper ： Update 条件封装

  AbstractLambdaWrapper ： 使用Lambda 语法

​    LambdaQueryWrapper ：用于Lambda语法使用的查询Wrapper

​    LambdaUpdateWrapper ： Lambda 更新封装Wrapper



#### 2. 创建测试类

```java
@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;
}
```



### 二、QueryWrapper

#### 1. 查询条件

查询名字中包含 n，年龄大于等于20且小于等于30，email 不为空的用户

```java
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
```



#### 2. 排序条件

按年龄降序查询用户，如果年龄相同则按id升序排列

```java
@Test
public void testWrapper02() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper
        .orderByDesc("age")
        .orderByAsc("id");
    List<User> userList = userMapper.selectList(queryWrapper);
    userList.forEach(System.out::println);
}
```



#### 3. 删除条件

删除 email 为空的所有用户，注意因为前面加了 **@TableLogic**，所以是逻辑删除，不是真的删了那条数据。

```java
@Test
public void testWrapper03() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.isNull("email");

    int row = userMapper.delete(queryWrapper);
    System.out.println("删除 " + row + " 条数据");
}
```



#### 4. 条件的优先级

查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 **user@github.com**

```java
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
```



#### 5. select子句

查询所有用户的用户名和年龄

```java
@Test
public void testWrapper05() {
    QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
    queryWrapper.select("name", "age");

    List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
    maps.forEach(System.out::println);
}
```



#### 6. 子查询

查询id不大于3的所有用户的id列表

```java
@Test
public void testWrapper06() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.inSql("id", "select id from user where id < 3");

    List<Object> objects = userMapper.selectObjs(queryWrapper);
    objects.forEach(System.out::println);
}
```

上面这个写法容易 sql 注入，可以用别的方式替换

```java
queryWrapper.lt("id", 3);
// 或者
queryWrapper.in("id", 1,2,3);
```



### 三、UpdateWrapper

#### 7. 条件优先级

查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 **user@github.com**

```java
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
```



### 四、Condition

#### 8. 动态组装查询条件

查询名字中包含 n，年龄大于20且小于30的用户，查询条件来源于用户输入，是可选的

```java
@Test
public void testWrapper08() {
    //定义查询条件，有可能为null（用户未输入）
    String name = null;
    Integer ageBegin = 20;
    Integer ageEnd = 30;

    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    if(StringUtils.isNotBlank(name)){
        queryWrapper.like("name","n");
    }
    if(ageBegin != null){
        queryWrapper.ge("age", ageBegin);
    }
    if(ageEnd != null){
        queryWrapper.le("age", ageEnd);
    }

    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}
```

上面这个写法没有问题，就是代码比较复杂，我们可以使用带 condition 参数的条件方法，简化代码：

```java
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
```



### 五、LambaWrapper

#### 9. Query-组装查询条件

查询名字中包含 n，年龄大于20且小于30的用户，查询条件来源于用户输入，是可选的

```java
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
```



#### 10. Update-条件更新

查询名字中包含n，且（年龄小于20或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 **user@github.com**

```java
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
```

