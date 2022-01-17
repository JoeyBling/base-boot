package com.tynet.config;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.tynet.frame.prj.ApplicationProperties;
import com.tynet.frame.prjext.MyApplicationProperties;
import com.tynet.saas.common.hessian.IAppProperties;
import com.tynet.saas.common.service.ICipherService;
import com.tynet.saas.common.service.adapter.IAppPropertiesAdapter;
import com.tynet.saas.common.service.impl.MD5CipherService;
import com.tynet.saas.common.service.impl.NoOpCipherService;
import com.tynet.saas.common.service.impl.SM4CipherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Objects;
import java.util.Optional;

/**
 * 自定义程序配置
 *
 * @author Created by 思伟 on 2021/12/3
 */
@Configuration
public class MyBootConfig {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 程序配置属性
     * <p>
     * {@link org.springframework.boot.context.properties.EnableConfigurationProperties} - 标识性自动配置
     * </p>
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

    /**
     * 默认{@link IAppProperties}实现
     */
    @Bean
    @ConditionalOnMissingBean
    public IAppProperties defaultAppProperties() {
        logger.warn("未检测到程序配置接口实现，适配默认实现...");
        return new IAppPropertiesAdapter() {
            /**
             * 默认单例实现
             * <p>
             * {@link com.google.common.base.Suppliers}
             * {@link org.springframework.util.function.SingletonSupplier}
             * </p>
             */
            protected final Supplier<? extends CacheConfig> DEFAULT_INSTANCE =
                    Suppliers.memoize(CacheConfig::new);

            @Override
            public CacheConfig getCacheConfig() {
                // 防止NPE
                return Optional.ofNullable(super.getCacheConfig()).orElseGet(DEFAULT_INSTANCE);
            }

        };
    }

    /**
     * 缓存配置 - 方便引用
     *
     * @param appProperties 程序配置
     * @return {@link IAppProperties.CacheConfig}
     */
    @Bean
    @ConditionalOnMissingBean
    public IAppProperties.CacheConfig cacheConfig(IAppProperties appProperties) {
        return Objects.requireNonNull(appProperties.getCacheConfig());
    }

    /**
     * 自定义程序配置属性
     */
    @Bean
    @ConditionalOnMissingBean
    public MyApplicationProperties myApplicationProperties() {
        return new MyApplicationProperties();
    }

    /**
     * 默认{@link ICipherService}实现
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public ICipherService defaultCipherService() {
        return NoOpCipherService.getInstance();
    }

    /**
     * MD5算法
     */
    @Bean
    @ConditionalOnMissingBean
    public MD5CipherService md5CipherService() {
        return new MD5CipherService();
    }

    /**
     * SM4国密算法
     */
    @Bean
    @ConditionalOnMissingBean
    public SM4CipherService sm4CipherService() {
        return new SM4CipherService();
    }

}
