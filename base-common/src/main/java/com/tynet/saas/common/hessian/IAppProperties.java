package com.tynet.saas.common.hessian;

import lombok.Data;
import org.apache.commons.io.FileUtils;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

/**
 * 程序配置接口
 *
 * @author Created by 思伟 on 2020/10/15
 */
public interface IAppProperties {

    /**
     * 获取客户端唯一标识
     *
     * @return String
     */
    String getClientId();

    /**
     * 是否是调试模式
     * <p>Defaults false
     *
     * @return boolean
     */
    default boolean isDebugMode() {
        return false;
    }

    /**
     * 获取域名
     * <p> 不能以{@code /}结尾
     *
     * @return String
     */
    String getDomain();

    /**
     * 获取系统本地存储根路径，绝对路径
     * <p>Defaults 系统临时目录
     *
     * @return String
     * @deprecated 多机部署需接入文件存储服务
     */
    @Deprecated
    default String getStoragePath() {
        return FileUtils.getTempDirectoryPath();
    }

    /**
     * 获取缓存配置
     *
     * @return {@link CacheConfig}
     */
    CacheConfig getCacheConfig();

    /**
     * 默认缓存配置
     */
    @Data
    class CacheConfig {
        /**
         * 默认生存时间
         * <p>
         * {@link #getExpire(String)}
         * </p>
         */
        private Duration defaultExpire = Duration.ofSeconds(3600);

        /**
         * 缓存对应的生存时间
         */
        private Map<String, Duration> expireMap;

        /**
         * 获取缓存生存时间
         *
         * @param name cache name.
         * @return Duration
         */
        public Duration getExpire(String name) {
            return Optional.ofNullable(this.expireMap.get(name)).orElse(this.defaultExpire);
        }
    }

}
