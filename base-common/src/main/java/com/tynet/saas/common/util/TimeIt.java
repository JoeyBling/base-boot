package com.tynet.saas.common.util;

import com.tynet.saas.common.function.VoidFunc;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * 计时工具类
 *
 * @author Created by 思伟 on 2020/11/23
 */
@Slf4j
public class TimeIt {

    /**
     * 基于{@link VoidFunc}进行耗时日志输出
     *
     * @see #printTime(VoidFunc, String, Logger)
     */
    public static void printTime(VoidFunc voidFunc, String message) throws Exception {
        printTime(voidFunc, message, null);
    }

    /**
     * 基于{@link VoidFunc}进行耗时日志输出
     *
     * @param voidFunc 自定义功能接口
     * @param message  日志消息
     * @param logger   日志输出
     */
    public static void printTime(VoidFunc voidFunc, String message, Logger logger) throws Exception {
        Objects.requireNonNull(voidFunc, "voidFunc is null");
        long startTime = System.currentTimeMillis();
        voidFunc.exec();
        // 输出消耗时间日志 - TODO 是否考虑`finally`输出？
        outLog(message, logger, startTime);
    }

    /**
     * 基于{@link Callable}进行耗时日志输出
     *
     * @see #printTime(Callable, String, Logger)
     */
    public static <V> V printTime(Callable<V> callable, String message) throws Exception {
        return printTime(callable, message, null);
    }

    /**
     * 基于{@link Callable}进行耗时日志输出
     *
     * @param callable 任务函数
     * @param message  日志消息
     * @param logger   日志输出
     * @param <V>
     * @return V
     * @throws Exception
     */
    public static <V> V printTime(Callable<V> callable, String message, Logger logger) throws Exception {
        Objects.requireNonNull(callable, "callable is null");
        long startTime = System.currentTimeMillis();
        V call = callable.call();
        // 输出消耗时间日志
        outLog(message, logger, startTime);
        return call;
    }

    /**
     * 基于{@link Function}进行耗时日志输出
     *
     * @param function
     * @param message  日志消息
     * @param logger   日志输出
     * @param <T>
     * @param <R>
     * @return Function
     */
    public static <T, R> Function<T, R> printTime(Function<T, R> function, String message, Logger logger) {
        Objects.requireNonNull(function, "function is null");
        return (t) -> {
            long startTime = System.currentTimeMillis();
            R apply = function.apply(t);
            // 输出消耗时间日志
            outLog(message, logger, startTime);
            return apply;
        };
    }

    /**
     * 统一输出日志
     *
     * @param message   日志消息
     * @param logger    日志输出 <p>Defaults {@link #log}
     * @param startTime 开始时间
     */
    protected static void outLog(final String message, final Logger logger, final long startTime) {
        // 这段是否需要？
        // Objects.requireNonNull(logger, "logger is null");
        // ((LocationAwareLogger) log).log(null, log.getName(), LocationAwareLogger.DEBUG_INT, message, null, null);
        final Logger outLog = Optional.ofNullable(logger).orElse(TimeIt.log);
        if (outLog.isDebugEnabled()) {
            // TODO 未获取到行号
            outLog.debug("{}计时结束,耗时[{}]",
                    StringUtils.defaultString(message),
                    Lazy.of(() -> (System.currentTimeMillis() - startTime) / 1000d + "s"));
        } else {
            /*outLog.info("{}计时结束,耗时[{}]",
                    StringUtils.defaultString(message),
                    Lazy.of(() -> (System.currentTimeMillis() - startTime) / 1000d + "s"));*/
        }
    }

    /**
     * just test
     */
    public static void main(String[] args) throws Exception {
        log.debug("{}", printTime(() -> {
            ExecutorUtil.sleep(Duration.ofMillis(200));
            return 1;
        }, "测试"));

        printTime(() -> {
            ExecutorUtil.sleep(Duration.ofSeconds(1));
        }, "自定义功能接口");

        final Function<Integer, Integer> testFunction = (Integer o) -> o + 1;
        log.debug("{}", printTime(testFunction, "Test", null).apply(2));
        log.debug("{}", printTime(testFunction, "Test", null));
    }

}
