package com.tynet.config;

import com.caucho.hessian.io.SerializerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hessian接口配置
 *
 * @author Updated by 思伟 on 2020/6/28
 */
@Configuration
public class HessianInterfaceConfiguration {

    /**
     * 默认传输的序列化方法的工厂
     *
     * @return {@link SerializerFactory}
     */
    @Bean
    @ConditionalOnMissingBean
    public SerializerFactory pageSerializerFactory() {
        return new SerializerFactory();
    }

}
