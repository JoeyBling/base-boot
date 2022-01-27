package com.tynet.saas.common.constant;

/**
 * 缓存管理器名称常量
 * <p>
 * 常见缓存管理器：
 * {@link org.springframework.cache.concurrent.ConcurrentMapCacheManager} - 基于{@link java.util.concurrent.ConcurrentHashMap}实现的Cache（默认）
 * {@link org.springframework.cache.support.SimpleCacheManager} - 针对给定缓存集合的简单缓存管理器
 * {@link org.springframework.cache.support.NoOpCacheManager} - 无操作（仅测试）
 * {@code EhCacheCacheManager} - 使用EhCache作为缓存技术
 * {@code RedisCacheManager} - 使用Redis作为缓存技术（来自`spring-data-redis`项目）
 * {@code GemfireCacheManager} - （来自`spring-data-gemfire`项目）
 * {@link org.springframework.cache.support.CompositeCacheManager} - 混合使用多种缓存时进行管理
 * </p>
 *
 * @author Created by 思伟 on 2021/1/30
 */
public class CacheManagerNameConst {

    /**
     * Redis
     */
    public static final String REDIS_CACHE_MANAGER = "redisCacheManager";

    /**
     * EhCache
     */
    public static final String EHCACHE_CACHE_MANAGER = "ehCacheCacheManager";

}
