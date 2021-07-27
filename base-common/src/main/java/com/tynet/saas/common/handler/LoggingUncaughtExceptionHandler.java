package com.tynet.saas.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义线程异常[终止]日志处理器
 * <p>
 * 不承认错误是让别人承担后果，承认错误是让自己承担后果
 * {@link Thread#setUncaughtExceptionHandler}
 * {@link ThreadGroup#uncaughtException} JVM默认异常处理
 * {@code java.lang.Thread#dispatchUncaughtException()}
 * </p>
 *
 * @author Created by 思伟 on 2021/2/21
 */
public class LoggingUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 系统默认的未捕获异常处理器
     */
    private Thread.UncaughtExceptionHandler defaultHandler;

    /**
     * zero-argument constructor
     */
    public LoggingUncaughtExceptionHandler() {
        // call self
        this(Thread.getDefaultUncaughtExceptionHandler());
    }

    /**
     * 使用给定的配置创建实例
     *
     * @param defaultHandler 系统默认的未捕获异常处理器
     */
    public LoggingUncaughtExceptionHandler(Thread.UncaughtExceptionHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
        // 设置此异常处理类为当前的线程处理 - 默认处理程序
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(t, e) && null != defaultHandler) {
            // 没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(t, e);
        }
    }

    /**
     * 处理异常
     *
     * @param t  Thread
     * @param ex Throwable
     * @return 是否处理了异常
     */
    protected boolean handleException(Thread t, Throwable ex) {
        // 为空不进行处理...
        if (null == ex) {
            return false;
        }
        if (logger.isErrorEnabled()) {
            logger.error(String.format("Unexpected error occurred executing thread " +
                    "'%s'.", t), ex);
        }
        return true;
    }

}
