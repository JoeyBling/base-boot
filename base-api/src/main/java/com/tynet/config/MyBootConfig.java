package com.tynet.config;

import com.tynet.frame.prjext.ApplicationProperties;
import com.tynet.frame.prjext.MyApplicationProperties;
import com.tynet.saas.common.hessian.IAppProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义程序配置
 *
 * @author Created by 思伟 on 2021/12/3
 */
@Configuration
public class MyBootConfig {

    /**
     * 程序配置属性
     * <p>
     * {@link org.springframework.boot.context.properties.EnableConfigurationProperties} - 标识性自动配置
     * </p>
     */
    @Bean
    @ConditionalOnMissingBean
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

    /**
     * 缓存配置 - 方便引用
     *
     * @param applicationProperties 程序配置
     * @return {@link IAppProperties.CacheConfig}
     */
    @Bean
    @ConditionalOnMissingBean
    public IAppProperties.CacheConfig cacheConfig(ApplicationProperties applicationProperties) {
        return applicationProperties.getCacheConfig();
    }

    /**
     * 自定义程序配置属性
     */
    @Bean
    @ConditionalOnMissingBean
    public MyApplicationProperties myApplicationProperties() {
        return new MyApplicationProperties();
    }

}
