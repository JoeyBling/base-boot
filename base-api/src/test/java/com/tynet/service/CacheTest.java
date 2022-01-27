package com.tynet.service;

import com.tynet.base.BaseAppTest;
import com.tynet.saas.common.hessian.IAppProperties;
import com.tynet.saas.common.service.ICacheService;
import com.tynet.service.mock.CacheTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 缓存测试
 *
 * @author Created by 思伟 on 2022/1/20
 */
public class CacheTest extends BaseAppTest {

    @Autowired
    protected IAppProperties appProperties;
    @Autowired
    protected CacheManager cacheManager;
    @Autowired
    private ObjectProvider<ICacheService> cacheServices;
    @Autowired
    private CacheTestService cacheTestService;

    /**
     * 基础测试
     */
    @Test
    public void baseTest() {
        final IAppProperties.CacheConfig cacheConfig = appProperties.getCacheConfig();
        logger.debug("-----本地缓存配置-----");
        logger.debug("默认生存时间：{}", cacheConfig.getDefaultExpire());
        for (Map.Entry<String, Duration> entry : cacheConfig.getExpireMap().entrySet()) {
            logger.debug("缓存名称：{}，生存时间：{}", entry.getKey(), entry.getValue());
        }

        logger.debug("-----Spring缓存配置-----");
        logger.debug("当前缓存管理器：{}", cacheManager);
        for (String cacheName : cacheManager.getCacheNames()) {
            logger.debug("缓存名称：{}", cacheName);
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
    }

}
