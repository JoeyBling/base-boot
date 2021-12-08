package com.tynet.saas.common.handler;

import com.tynet.saas.common.util.ReflectionUtils;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

/**
 * 自定义错误日志处理器
 * <p>
 * 处理异步执行的任务异常
 * {@link org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler#setErrorHandler}
 * </p>
 *
 * @author Created by 思伟 on 2021/4/8
 */
public class LoggingErrorHandler implements ErrorHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 是否引发异常 - Whether to throw an exception
     * <p>Defaults {@code true}.
     */
    @Setter
    protected boolean throwException = false;

    @Override
    public void handleError(Throwable t) {
        if (logger.isErrorEnabled()) {
            logger.error("Unexpected error occurred in scheduled task.", t);
        }
        if (isThrowException()) {
            // 适配抛出运行时异常
            ReflectionUtils.rethrowRuntimeException(t);
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
