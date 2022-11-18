package com.tynet.config;

import com.tynet.saas.common.handler.LoggingCacheErrorHandler;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

/**
 * Spring-Cache配置类
 * <p>参考：https://blog.csdn.net/s674334235/article/details/82593899
 *
 * @author Created by 思伟 on 2020/4/23
 */
@Configuration
@EnableCaching(proxyTargetClass = true)
public class MyCacheConfig implements CachingConfigurer {

    /**
     * 重写这个方法，目的是用以提供默认的cacheManager
     * <p>
     * 缓存类型对应的配置 - {@code CacheConfigurations}
     * 最简单的缓存配置（默认） - {@code SimpleCacheConfiguration}
     * 自动导入缓存配置类 - {@code CacheConfigurationImportSelector}
     * {@link org.springframework.context.annotation.ImportSelector} - SpringBoot自动装配(方法返回需要注入的bean类全路径字符串数组)
     * {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration} - 开启Spring上下文的自动配置{@code AutoConfigurationImportSelector#getCandidateConfigurations}
     * </p>
     */
    @Override
    public CacheManager cacheManager() {
        return CachingConfigurer.super.cacheManager();
    }

    /**
     * 如果cache出错， 我们会记录在日志里，方便排查，比如反序列化异常
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new LoggingCacheErrorHandler();
    }

}
