package com.tynet.saas.common.handler;

import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * 自定义异步方法异常日志处理器 - 引发的未捕获异常的策略
 * A default {@link AsyncUncaughtExceptionHandler} that simply logs the exception.
 * 被`@Async`注解的方法在独立线程调用，不能被`@ControllerAdvice`全局异常处理器捕获，所以需要自己设置异常处理
 *
 * @author Created by 思伟 on 2020/11/24
 * @see org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
 */
/* non-public */ public class LoggingAsyncUncaughtExceptionHandler extends SimpleAsyncUncaughtExceptionHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 是否引发异常 - Whether to throw an exception
     * <p>Defaults {@code false}.
     *
     * @deprecated {@link org.springframework.aop.interceptor.AsyncExecutionAspectSupport} 拦截方法自己处理了
     */
    @Setter
    @Deprecated
    protected boolean throwException = false;

    /**
     * {@link org.springframework.aop.interceptor.AsyncExecutionInterceptor#invoke}
     * {@link org.springframework.aop.interceptor.AsyncExecutionAspectSupport}
     */
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        if (logger.isErrorEnabled()) {
            logger.error(String.format("Unexpected error occurred invoking async " +
                    "method '%s'.", method), ex);
            if (ObjectUtils.isNotEmpty(params)) {
                for (Object param : params) {
                    logger.error("Parameter value - " + param);
                }
            }
        }
        if (isThrowException()) {
            // Throw it...
            throw new RuntimeException(ex);
        }
    }

    /**
     * 是否引发异常
     *
     * @return boolean
     */
    public boolean isThrowException() {
        return Boolean.TRUE.equals(throwException);
    }

}
