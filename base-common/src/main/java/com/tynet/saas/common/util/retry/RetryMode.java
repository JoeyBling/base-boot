package com.tynet.saas.common.util.retry;

import cn.hutool.core.map.MapUtil;

import java.util.Collections;
import java.util.Map;

/**
 * 重试运行模式
 *
 * @author Created by 思伟 on 2021/9/10
 */
public enum RetryMode {
    /**
     * 调用线程与重试线程相同
     */
    PROCEED("PROCEED", "继续") {
        @Override
        public boolean isAsync() {
            return false;
        }
    },

    /**
     * 调用线程与重试线程不同 - 异步阻塞模式
     */
    SYNC("SYNC", "同步") {
        @Override
        public boolean isAsync() {
            return false;
        }
    },

    /**
     * 异步非阻塞模式
     */
    ASYNC("ASYNC", "异步") {
        /**
         * 缓存扩展参数
         */
        final Map<String, Object> CACHE_EXTRA_PARAMS = MapUtil.<String, Object>builder()
                .put("test_key", "just_test")
                .build();

        @Override
        public boolean isAsync() {
            return true;
        }

        @Override
        public Map<String, ?> getExtraParams() {
            return CACHE_EXTRA_PARAMS;
        }

        @Override
        public void testHandle() {
            // NO-OPS...
        }
    },

    ;

    /**
     * Key(代码)
     */
    private String key;

    /**
     * 值(含义)
     */
    private String value;

    /**
     * default constructor
     *
     * @param key   Key
     * @param value 值
     */
    RetryMode(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 是否异步
     *
     * @return boolean
     */
    public abstract boolean isAsync();

    /**
     * 获取扩展参数
     *
     * @return Map
     */
    public Map<String, ?> getExtraParams() {
        return Collections.emptyMap();
    }

    /**
     * 测试操作
     */
    public void testHandle() {
        throw new AbstractMethodError();
    }

    /**
     * 获取Key(代码)
     *
     * @return String
     */
    public String getKey() {
        return key;
    }

    /**
     * 获取值(含义)
     *
     * @return String
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.key;
    }

}
