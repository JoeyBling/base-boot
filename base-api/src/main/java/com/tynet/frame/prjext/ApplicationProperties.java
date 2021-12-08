package com.tynet.frame.prjext;

import com.tynet.saas.common.hessian.IStartUp;
import com.tynet.saas.common.service.adapter.IAppPropertiesAdapter;
import com.tynet.saas.common.util.DateUtils;
import com.tynet.saas.common.util.ExceptionUtil;
import com.tynet.saas.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 程序配置属性
 * <p>
 * {@link javax.validation.constraints.NotEmpty} - 用在集合上面(不能注释枚举)
 * {@link javax.validation.constraints.NotBlank} - Accepts {@code CharSequence}.
 * {@link javax.validation.constraints.NotNull} - 不能为{@code null}
 * </p>
 *
 * @author Created by 思伟 on 2021/12/2
 */
@Data
@Validated
@Primary
@Component
@ConfigurationProperties(prefix = ApplicationProperties.APPLICATION_CONFIG_PREFIX)
@ConfigurationPropertiesBinding
public class ApplicationProperties extends IAppPropertiesAdapter implements IStartUp {
    /**
     * 程序配置属性前缀
     */
    public static final String APPLICATION_CONFIG_PREFIX = "application";

    /**
     * 启动时间戳
     */
    static final long START_UP_TIMESTAMP = DateUtils.currentTimeStamp();

    /**
     * 调试模式
     */
    @Nullable
    private Boolean debug;

    /**
     * 客户端唯一标识
     */
    @Nullable
    private String clientId;

    /**
     * 程序名称
     */
    @NotBlank
    private String name;

    /**
     * 版本号
     */
    @NotBlank
    private String version;

    /**
     * 主页
     */
    @Nullable
    private String url;

    /**
     * 域名
     */
    @NotBlank
    private String domain;

    /**
     * 系统本地存储根路径，绝对路径
     * <p>Defaults 系统临时目录
     */
    private String storagePath = FileUtils.getTempDirectoryPath();

    /**
     * 缓存配置
     */
    @NestedConfigurationProperty
    private CacheConfig cacheConfig = new CacheConfig();

    @NestedConfigurationProperty
    private Task task = new Task();

    @NestedConfigurationProperty
    private Mvc mvc = new Mvc();

    @Override
    public String getClientId() {
        // 默认取应用程序启动时间戳
        return StringUtils.defaultIfBlank(clientId, String.valueOf(START_UP_TIMESTAMP));
    }

    @Override
    public boolean isDebugMode() {
        return Boolean.TRUE.equals(debug);
    }

    @Deprecated
    @DeprecatedConfigurationProperty(reason = "暂时弃用")
    public String getUrl() {
        return url;
    }

    @Override
    public void startUp() throws Exception {
        // call itself
        this.init();
    }

    /**
     * 执行初始化操作
     */
    public void init() {
        // 原子性操作
        if (!initFlag.compareAndSet(false, true)) {
            return;
        }
        Assert.hasText(this.getStoragePath(), "本地存储根路径不能为空");
        try {
            // 创建父级文件夹
            FileUtils.forceMkdir(FileUtils.getFile(this.getStoragePath()));
        } catch (IOException e) {
            // 适配抛出运行时异常
            throw ExceptionUtil.wrapRuntimeException(e);
        }
        Assert.isTrue(FileUtils.getFile(this.getStoragePath()).exists(), "本地存储根路径必须存在");
    }

    /**
     * 任务相关配置
     */
    @Data
    public static class Task {

        /**
         * 线程池配置
         */
        @NestedConfigurationProperty
        private ThreadPoolConfig threadPool = new ThreadPoolConfig();
    }

    /**
     * 线程池配置
     * <p>
     * 参考：{@link org.springframework.boot.autoconfigure.task.TaskExecutionProperties}
     * </p>
     */
    @Data
    public static class ThreadPoolConfig {
        /**
         * 线程池维护线程的最少数量
         */
        private int corePoolSize = 2;

        /**
         * 线程池维护线程的最大数量
         */
        private int maxPoolSize = 1000;

        /**
         * 线程池所使用的缓冲队列
         */
        private int queueCapacity = 200;

        /**
         * 线程池维护线程所允许的空闲时间
         */
        private Duration keepAlive = Duration.ofSeconds(60);

        /**
         * 配置线程池中的线程的名称前缀
         */
        private String threadNamePrefix = "async-task-";
    }

    /**
     * Mvc配置
     */
    @Data
    public static class Mvc {

        // @NestedConfigurationProperty
        private List<ViewResolver> viewResolvers;

        // @NestedConfigurationProperty
        private List<Cors> cors = Arrays.asList(new Cors());

        /**
         * 自定义简单响应自动控制器
         */
        @Data
        public static class ViewResolver {

            /**
             * URL路径（或模式）
             * <p>Patterns such as {@code "/admin/**"} or {@code "/articles/{name:\\w+}"}
             */
            private String urlPath;

            /**
             * 视图名称
             */
            private String viewName;

        }

        /**
         * CORS跨域配置
         */
        @Data
        public static class Cors {
            /**
             * Wildcard representing <em>all</em> origins, methods, or headers.
             */
            public static final String ALL = "*";

            /**
             * 允许跨域访问的域名
             */
            private List<String> allowedOrigins = Collections.singletonList(ALL);

            /**
             * 允许跨域的请求头
             */
            private List<String> allowedHeaders = Collections.singletonList(ALL);

            /**
             * 允许跨域的请求方法
             */
            private List<HttpMethod> allowedMethods = Collections.unmodifiableList(
                    Arrays.asList(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS));

            /**
             * 是否支持用户凭据
             */
            @Nullable
            private Boolean allowCredentials;

            /**
             * 请求的响应时间 - 单位：秒
             */
            private Long maxAge = 3600L;

            /**
             * 路径模式
             */
            @NotNull
            @NotBlank
            private String pathPattern = "/**";
        }

    }

    /**
     * 是否已初始化完成
     *
     * @return boolean
     */
    public final boolean isInitialized() {
        return Boolean.TRUE.equals(initFlag.get());
    }

    /**
     * 初始化标识
     */
    @Getter(AccessLevel.NONE)
    protected final AtomicBoolean initFlag = new AtomicBoolean(false);

}
