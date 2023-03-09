package com.tynet.config;

import com.caucho.hessian.io.SerializerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hessian接口配置
 *
 * @author Updated by 思伟 on 2020/6/28
 * @deprecated 逐步弃用RPC远程技术支持
 */
@Deprecated
@Configuration
public class HessianInterfaceConfiguration {

    /**
     * 序列化程序工厂
     *
     * @return {@link SerializerFactory}
     */
    @Bean
    @ConditionalOnMissingBean
    public SerializerFactory defaultSerializerFactory() {
        // 默认传输的序列化方法的工厂
        return SerializerFactory.createDefault();
    }

}
