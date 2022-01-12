package com.tynet.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus
 *
 * @author Created by 思伟 on 2022/1/10
 */
@Configuration
@org.mybatis.spring.annotation.MapperScan({
        "com.tynet.**.dao*",
        "com.tynet.**.mapper*",
})
public class MyBatisPlusConfig {

    /**
     * 分页插件 - 最新版
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

}
