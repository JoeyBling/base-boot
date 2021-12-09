package com.tynet.config;

import com.tynet.frame.prj.ApplicationProperties;
import com.tynet.saas.common.handler.LoggingAsyncUncaughtExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义异步配置类
 *
 * @author Created by 思伟 on 2020/7/13
 */
@Configuration
@EnableAsync(proxyTargetClass = true)
public class MyAsyncConfig implements AsyncConfigurer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 自定义Spring线程池
     *
     * @return TaskExecutor
     */
    @Bean
    // @Bean(name = org.springframework.aop.interceptor.AsyncExecutionAspectSupport.DEFAULT_TASK_EXECUTOR_BEAN_NAME)
    @ConditionalOnMissingBean
    public TaskExecutor asyncExecutor(ApplicationProperties properties) {
        // 线程池配置
        final ApplicationProperties.ThreadPoolConfig poolConfig = properties.getTask().getThreadPool();

        // 此处可以自定义扩展实现 - 拦截、日志等
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        threadPoolTaskExecutor.setCorePoolSize(poolConfig.getCorePoolSize());
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        // 最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(poolConfig.getMaxPoolSize());
        // 配置缓冲队列大小
        threadPoolTaskExecutor.setQueueCapacity(poolConfig.getQueueCapacity());
        // 线程所允许的空闲时间 - 单位：秒
        threadPoolTaskExecutor.setKeepAliveSeconds(Math.toIntExact(poolConfig.getKeepAlive().getSeconds()));
        // 配置线程池前缀
        threadPoolTaskExecutor.setThreadNamePrefix(poolConfig.getThreadNamePrefix());
        // 拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // threadPoolTaskExecutor.setRejectedExecutionHandler(new PrintingPolicy());
        threadPoolTaskExecutor.setThreadFactory(null);
        /**
         * 执行初始化
         * <p>
         * 本身就是SpringBean,非必要执行 {@link org.springframework.scheduling.concurrent.ExecutorConfigurationSupport#afterPropertiesSet()} 会触发调用2次了
         * 需要执行初始化方法的是因为代理的问题，例如{@link org.springframework.scheduling.config.TaskExecutorFactoryBean#afterPropertiesSet()}
         * {@link ThreadPoolTaskExecutor#getThreadPoolExecutor()}
         * </p>
         */
        // threadPoolTaskExecutor.afterPropertiesSet();
        return threadPoolTaskExecutor;
    }

    /**
     * 自定义异步任务线程池，若不重写会使用默认的线程池
     * <p>
     * {@linkplain org.springframework.core.task.SimpleAsyncTaskExecutor 简单异步TaskExecutor}
     * 默认执行器名称 {@link org.springframework.aop.interceptor.AsyncExecutionAspectSupport#DEFAULT_TASK_EXECUTOR_BEAN_NAME}
     * {@link org.springframework.aop.interceptor.AsyncExecutionInterceptor#getDefaultExecutor} 获取默认执行器
     * </p>
     */
    @Override
    public Executor getAsyncExecutor() {
        logger.debug("初始化默认自定义异步任务线程池...");
        // return null;
        return new SimpleAsyncTaskExecutor("simple-async-task-");
        // return asyncExecutor(null);
    }

    /**
     * 异常处理器
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new LoggingAsyncUncaughtExceptionHandler();
    }

}
