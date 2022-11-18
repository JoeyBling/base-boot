package com.tynet.service;

import com.tynet.base.BaseAppTest;
import com.tynet.saas.common.hessian.IAppProperties;
import com.tynet.saas.common.service.ICacheService;
import com.tynet.service.mock.CacheTestService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 缓存测试
 *
 * @author Created by 思伟 on 2022/1/20
 */
public class CacheTest extends BaseAppTest {

    @Autowired
    protected CacheManager cacheManager;
    @Autowired
    protected IAppProperties appProperties;
    @Autowired
    private ObjectProvider<ICacheService> cacheServices;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 响应式/反应式编程方式
     */
    @Autowired(required = false)
    private ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate;
    @Autowired
    private CacheTestService cacheTestService;

    /**
     * 基础测试
     */
    @Test
    public void baseTest() {
        logger.debug("-----Spring缓存配置-----");
        logger.debug("当前缓存管理器：{}", cacheManager);
        for (String cacheName : cacheManager.getCacheNames()) {
            logger.debug("缓存名称：{}", cacheName);
        }

        final IAppProperties.CacheConfig cacheConfig = appProperties.getCacheConfig();
        logger.debug("-----本地缓存配置-----");
        logger.debug("默认生存时间：{}", cacheConfig.getDefaultExpire());
        for (Map.Entry<String, Duration> entry : cacheConfig.getExpireMap().entrySet()) {
            logger.debug("缓存名称：{}，生存时间：{}", entry.getKey(), entry.getValue());
        }

        logger.debug("-----自定义缓存接口实现-----");
        for (ICacheService cacheService : cacheServices.orderedStream().collect(Collectors.toList())) {
            logger.debug("缓存名称：{}，缓存时间：{}", cacheService.getCacheName(), cacheService.getCacheTime());
        }
    }

    /**
     * 缓存测试
     */
    @Test
    public void cacheTest() {
        // 缓存键
        final String cacheKey = "zhousiwei";
        // 缓存空值
        Assertions.assertNull(cacheTestService.cacheNullVal(cacheKey));
        // 删除所有缓存
        cacheTestService.clearAll();
    }

    /**
     * 模板测试
     */
    @Test
    public void templateTest() {
        logger.debug("当前服务器时间：{}", this.time());
        // 缓存键
        final String cacheKey = "JoeyBling";

        redisTemplate.opsForValue().setIfAbsent(cacheKey, true, Duration.ofMinutes(5));
        redisTemplate.opsForHash().putIfAbsent("TEST_NODE", cacheKey, true);

        // redisTemplate.execute()
    }

    /**
     * 模板测试
     */
    @Test
    public void reactiveTemplateTest() {
        Assertions.assertNotNull(reactiveRedisTemplate);
        final long time = reactiveRedisTemplate.execute(connection -> connection.serverCommands().time())
                .toStream().findFirst().get();
        logger.debug("当前服务器时间：{}", time);
    }

    /**
     * 当前服务器时间
     * <p>
     * {@code TIME}
     * 单台服务器可直接用系统时间 (多服务器可以使用redis时间+客户端标识等)
     * </p>
     *
     * @param timeUnit 时间单位
     * @return long
     */
    public long time(@NotNull final TimeUnit timeUnit) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> connection.time(timeUnit));
    }

    /**
     * 当前服务器时间
     * <p>单位（毫秒）.
     */
    public long time() {
        return this.time(TimeUnit.MILLISECONDS);
    }

}
