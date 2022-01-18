package com.frankeleyn;

import com.frankeleyn.entity.Product;
import com.frankeleyn.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Frankeleyn
 * @date 2022/1/17 18:54
 */
@SpringBootTest
public class ProductTest {

    @Autowired
    private ProductMapper productMapper;

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
        System.out.println("最后的结果：" + product.getPrice()); // 最后的结果：70

    }
}
