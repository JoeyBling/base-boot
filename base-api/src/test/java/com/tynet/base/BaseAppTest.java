package com.tynet.base;

import com.tynet.App;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * SpringBoot测试基类
 * <p>
 * 默认情况下，在每个 JPA 测试结束时，事务会发生回滚。这在一定程度上可以防止测试数据污染数据库
 * 测试持久层时，默认是回滚的。可以在具体的测试方法上添加@Rollback(false)来禁止回滚，也可以在测试类上添加
 * 由于Test没有启动web容器，所以SpringBoot JUnit 全局过滤器和监听器会失效
 * 如果加上了`@Transactional`注解，读取的数据都有缓存
 * </p>
 *
 * @author Created by 思伟 on 2020/6/6
 */
// @ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
// @ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
@SpringBootTest(classes = App.class)
//@Transactional
// true ? 测试数据不会污染数据库 : 会真正添加到数据库当中
//@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import(WebConfigTest.class)
public abstract class BaseAppTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeEach
    // @BeforeAll
    public void setUp() throws Exception {
    }

    /**
     * 防止程序提前结束
     * 不建议使用，会导致事务出问题
     */
    @Deprecated
    protected void avoidExit() {
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
