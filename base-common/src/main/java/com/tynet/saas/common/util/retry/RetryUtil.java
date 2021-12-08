package com.tynet.saas.common.util.retry;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * 重试工具类
 * <p>
 * {@link BiFunction} - 接受两个输入参数的方法，并且返回一个结果
 * 参考资料（重试工具库）：
 * 1. Guava-Retrying：https://github.com/rholder/guava-retrying
 * 2. Spring-Retry：https://github.com/spring-projects/spring-retry
 * 3. 西西弗斯：https://github.com/houbb/sisyphus
 * </p>
 *
 * @author Created by 思伟 on 2021/8/20
 */
public class RetryUtil {
    /**
     * 默认限制最大重试次数
     */
    @Deprecated
    public static final int DEFAULT_LIMIT_MAX_RETRIES = 10;

    /**
     * <p>
     * 参考资料：https://zhuanlan.zhihu.com/p/98154352
     * </p>
     *
     * @param supplier
     * @param delayRetryPeriod
     * @param consumer
     * @param <T>
     * @return
     */
    public static <T> T retrySync(@NotNull final Supplier<T> supplier,
                                  @NotNull List<Duration> delayRetryPeriod,
                                  final BiFunction<T, Exception, Boolean> consumer) {
        final IRetryStrategyAble retryStrategy = new PeriodRetryStrategy(delayRetryPeriod);
        for (int attemptNumber = 1; ; attemptNumber++) {
            try {
                return supplier.get();
            } catch (Exception e) {

            }
        }
        /*T result = null;
        Exception exception = null;

        int retryCount = 0;
        boolean callMethod = true;
        while (callMethod && retryCount <= maxRetryCount) {
            try {
                // 获取调用服务的结果
                result = supplier.get();
            } catch (Exception e) {
                // 如果重试次数不小于最大重试次数，就抛出异常，我们把对异常的处理交给业务方
                if (retryCount >= maxRetryCount) {
                    throw e;
                }
                exception = e;
            }
            // 对结果进行判断
            callMethod = consumer.apply(result, exception);
            if (callMethod) {
                retryCount++;
            }
        }
        return result;*/
    }

}
