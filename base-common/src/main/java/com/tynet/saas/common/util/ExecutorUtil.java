package com.tynet.saas.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.tynet.saas.common.exception.SysRuntimeException;
import com.tynet.saas.common.service.IResponse;
import com.tynet.saas.common.service.decorator.PrefixSlfLoggerDecorator;
import com.tynet.saas.common.service.impl.SimpleResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 执行器工具类
 * <p>
 * 列出的执行器：
 * 1. {@link ScheduledExecutorService} 调度器服务
 * 2. {@link ThreadPoolExecutor} 线程池服务
 * </p>
 *
 * @author Created by 思伟 on 2021/8/16
 */
public class ExecutorUtil {
    /**
     * 默认等待终止时间量
     */
    public static final Duration DEFAULT_AWAIT_TERMINATION_TIME = Duration.ofMinutes(5);
    /**
     * 无限等待终止时间量
     * <p>
     * default netty value == Infinity. We really *must* wait for this to complete
     * </p>
     */
    public static final Duration INFINITY_AWAIT_TERMINATION_TIME = Duration.ofSeconds(1000 * 60);

    /**
     * 执行器是否已关闭
     *
     * @param executor 执行器
     * @return boolean
     */
    public static boolean isShutdown(@NotNull final Executor executor) {
        Assert.notNull(executor, "executor must not be null");
        if (executor instanceof ExecutorService) {
            if (((ExecutorService) executor).isShutdown()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对基础{@link ExecutorService}服务执行关机 - 关闭执行器
     * <p>
     * 参考：{@link org.springframework.scheduling.concurrent.ExecutorConfigurationSupport#destroy()}
     * </p>
     *
     * @param executor                         执行器
     * @param waitForTasksToCompleteOnShutdown 是否等待任务完成再关闭 <p>Defaults {@code false}.
     * @param awaitTerminationTime             等待终止时间量
     * @return 响应对象（执行器是否已关闭、未执行的任务列表）
     */
    public static @NotNull IResponse<List<Runnable>> shutdownExecutor(@Nullable final ExecutorService executor,
                                                                      @Nullable Boolean waitForTasksToCompleteOnShutdown,
                                                                      final Duration awaitTerminationTime) {
        if (null == executor || isShutdown(executor)) {
            return SimpleResponse.success(null);
        }
        // 错误消息 - 不为空标识失败
        String errMsg = null;
        // 未执行的任务列表
        List<Runnable> unExecutedRunnableList = null;
        if (Boolean.TRUE.equals(waitForTasksToCompleteOnShutdown)) {
            /**
             * {@link ExecutorService#shutdown()} 停止接收新任务，原来的任务继续执行（包括队列里等待的任务）
             * {@link ExecutorService#shutdownNow()} 停止接收新任务，原来的任务停止执行（丢弃队列里等待的任务）
             * {@link ExecutorService#awaitTermination(long, TimeUnit)} 当前线程阻塞
             */
            executor.shutdown();
        } else {
            // 未执行的任务列表
            unExecutedRunnableList = executor.shutdownNow();
        }
        try {
            // 等待现有任务终止
            if (!awaitTermination(executor, awaitTerminationTime)) {
                // 理论上来讲可以不处理此种情况(应该不会出现这种情况)
                errMsg = StringUtils.format("等待执行器终止时超时,timeout={}...有可能出现线程阻塞", awaitTerminationTime);
                // 超时的时候向线程池中所有的线程发出中断(可选)
                // executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // Restore the interrupted status - 让调用栈中更高层的代码进行处理(不能吞噬该异常并继续运行)
            Thread.currentThread().interrupt();
            errMsg = "等待执行器终止时线程中断...";
            // 忽略抛出异常 - IGNORE
            // throw new RuntimeException(e);
        }
        return new SimpleResponse<>(
                /**
                 * 判断执行器是否已关闭 & 关闭后所有任务都已完成(真正终结)
                 * <p>
                 * 除非首先调用 `shutdown` 或 `shutdownNow`,否则 `isTerminated` 永不为 true
                 * </p>
                 */
                isShutdown(executor) && executor.isTerminated(), errMsg, unExecutedRunnableList);
    }

    /**
     * 等待现有任务终止
     * <p>
     * 一般情况下会和{@link ExecutorService#shutdown()}组合使用
     * </p>
     *
     * @param executor             执行器
     * @param awaitTerminationTime 等待终止时间量
     * @return 是否已关闭（终结）
     * @throws InterruptedException if interrupted while waiting
     */
    public static boolean awaitTermination(@NotNull final ExecutorService executor,
                                           @NotNull final Duration awaitTerminationTime) throws InterruptedException {
        /**
         * wait for this to complete - 等待现有任务终止
         * <p>
         * 使用{@link TimeUnit#NANOSECONDS} 纳秒更精确的单位
         * </p>
         */
        return executor.awaitTermination(awaitTerminationTime.toNanos(), TimeUnit.NANOSECONDS);
    }

    /**
     * 循环等待终止 - Wait a while for existing tasks to terminate
     *
     * @throws InterruptedException if interrupted while waiting
     */
    public static void loopAwaitTermination(@NotNull final ExecutorService executor,
                                            @NotNull final Duration awaitTerminationTime) throws InterruptedException {
        // 如果未关闭则进行循环
        while (!awaitTermination(executor, awaitTerminationTime)) {
        }
    }

    /**
     * 取消未执行的任务
     * <p>
     * 一般情况下会和{@link ExecutorService#shutdownNow()}组合使用
     * </p>
     *
     * @param task 任务 - 通常为 {@link RunnableFuture}
     * @return 是否成功（包括已完成）
     */
    public static boolean cancelRemainingTask(Runnable task) {
        if (task instanceof Future) {
            return cancelTask((Future<?>) task);
        }
        return false;
    }

    /**
     * 立即停止任务
     *
     * @see #cancelTask(Future, Duration)
     */
    public static boolean cancelTask(final Future<?> future) {
        return cancelTask(future, null);
    }

    /**
     * 取消正在运行的任务
     *
     * @param future    任务
     * @param delayTime 延时时间量
     * @return 是否成功（包括已完成）
     */
    public static boolean cancelTask(final Future<?> future, final Duration delayTime) {
        // 是否在完成之前被取消
        if (!future.isCancelled()) {
            sleep(delayTime);
            // 停止任务 - 直接中断正在执行的任务
            return future.cancel(true);
        }
        return future.isDone();
    }

    /**
     * 线程睡眠
     * <p>
     * 使用{@link TimeUnit#NANOSECONDS} 纳秒更精确的单位
     * </p>
     *
     * @param sleepTime 时间量
     */
    public static void sleep(@Nullable Duration sleepTime) {
        if (null != sleepTime) {
            try {
                TimeUnit.NANOSECONDS.sleep(sleepTime.toNanos());
            } catch (InterruptedException e) {
                // Restore the interrupted status - 让调用栈中更高层的代码进行处理(不能吞噬该异常并继续运行)
                Thread.currentThread().interrupt();
                throw ExceptionUtil.wrapRuntimeException(e);
            }
        }
    }

    /**
     * 获取执行器名称
     *
     * @param executor 执行器
     * @return 执行器名称
     */
    @Nullable
    public static String getExecutorName(@Nullable ExecutorService executor) {
        final Supplier<String> executorNameSupplier = () -> {
            if (null == executor) {
                // NPE
            } else if (ScheduledExecutorService.class.isAssignableFrom(executor.getClass())) {
                /**
                 * 调度器服务
                 * <p>
                 * 顺序必须在线程池服务之前
                 * 因为{@link ScheduledThreadPoolExecutor})继承了线程池服务
                 * </p>
                 */
                return "调度器";
            } else if (ThreadPoolExecutor.class.isAssignableFrom(executor.getClass())) {
                // 线程池服务
                return "线程池";
            } else {
                throw new SysRuntimeException(
                        StringUtils.format("未处理的执行器对象[{}]",
                                executor.getClass().getName())
                );
            }
            return null;
        };
        return executorNameSupplier.get();
    }

    /**
     * 必须关闭执行器
     *
     * @param executor                         执行器
     * @param waitForTasksToCompleteOnShutdown 是否等待任务完成再关闭 <p>Defaults {@code false}.
     * @param awaitTerminationTime             等待终止时间量
     * @param logger                           日志对象
     */
    public static void mustShutdownExecutor(@Nullable final ExecutorService executor,
                                            @Nullable Boolean waitForTasksToCompleteOnShutdown,
                                            final Duration awaitTerminationTime,
                                            @NotNull final Logger logger) {
        if (null == executor || isShutdown(executor)) {
            // 不处理
            return;
        }
        // 日志前缀
        final String logPrefix = StringUtils.format("[{}]", getExecutorName(executor));
        // 装饰后的日志对象
        final PrefixSlfLoggerDecorator decorateLogger = new PrefixSlfLoggerDecorator(logger, logPrefix);
        if (decorateLogger.isInfoEnabled()) {
            decorateLogger.info("关闭执行器服务init");
        }
        // 结果的提供者
        final Supplier<IResponse<List<Runnable>>> responseSupplier = () -> {
            try {
                return TimeIt.printTime(
                        () -> shutdownExecutor(
                                executor,
                                Optional.ofNullable(waitForTasksToCompleteOnShutdown).orElse(false),
                                awaitTerminationTime
                        ), "关闭执行器服务", decorateLogger);
            } catch (Exception e) {
                // 适配抛出运行时异常
                ReflectionUtils.rethrowRuntimeException(e);
                // 不会执行
                throw new RuntimeException(e);
            }
        };
        final IResponse<List<Runnable>> response = responseSupplier.get();
        Assert.isTrue(response.isSuccess(),
                StringUtils.format("关闭执行器失败：{}",
                        StringUtils.defaultIfEmpty(response.getErrMsg(), "未知异常")
                )
        );
        // 未执行的任务列表
        final List<Runnable> unExecutedRunnableList = response.getData();
        if (CollectionUtil.isNotEmpty(unExecutedRunnableList)) {
            decorateLogger.warn("已经丢弃的任务数量:[{}]", unExecutedRunnableList.size());
            for (Runnable runnable : unExecutedRunnableList) {
                // 取消未执行的任务
                if (!ExecutorUtil.cancelRemainingTask(runnable)) {
                    // decorateLogger.error("取消未执行的任务[{}]失败...");
                    decorateLogger.error("取消未执行的任务失败...");
                }
            }
        }
        if (decorateLogger.isInfoEnabled()) {
            decorateLogger.info("关闭执行器服务success");
        }
    }

    /**
     * just test
     */
    public static void main(String[] args) {
        sleep(null);
        sleep(Duration.ofMillis(1000 * 2));
    }

}
