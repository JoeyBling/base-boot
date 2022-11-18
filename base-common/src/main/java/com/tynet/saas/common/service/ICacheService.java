package com.tynet.saas.common.service;

import java.time.Duration;

/**
 * 自定义缓存接口
 *
 * @author Created by 思伟 on 2020/11/27
 */
public interface ICacheService {

    /**
     * 获取缓存名
     *
     * @return String
     */
    String getCacheName();

    /**
     * 获取缓存时间
     * <p>
     * 请严格对应cache配置的缓存生存时间-同步更改`spring-context-cache.xml` or SpringBoot对应的Yml配置
     * </p>
     *
     * @return 时间量
     */
    Duration getCacheTime();

    /**
     * 获取缓存Key前缀,不含分隔符
     * <p>
     * 组合多个缓存注解：{@link org.springframework.cache.annotation.Caching}
     * {@link org.springframework.cache.interceptor.CacheAspectSupport}
     * </p>
     *
     * @return String
     */
    default String getCachePrefix() {
        return this.getCacheName();
    }

}
