package com.tynet.saas.common.function;

import com.tynet.saas.common.util.ExceptionUtil;

/**
 * 自定义运行的功能接口 - A throwing {@link Runnable}
 * <p>
 * 允许抛出已检查的异常. - Allows for checked exceptions to be thrown.
 * </p>
 *
 * @author Created by 思伟 on 2022/3/4
 */
public interface ThrowingRunnable extends Runnable {

    /**
     * 执行函数
     *
     * @throws Exception in case of errors
     */
    void doRun() throws Exception;

    /**
     * {@inheritDoc}
     */
    @Override
    default void run() {
        try {
            this.doRun();
        } catch (Exception e) {
            // 处理异常
            this.handleException(e);
        }
    }

    /**
     * 处理异常
     * <p>
     * Implement your own exception handling logic here..
     * </p>
     *
     * @param e 异常对象
     */
    default void handleException(Exception e) {
        // 适配抛出运行时异常
        throw ExceptionUtil.wrapRuntimeException(e);
    }

}
