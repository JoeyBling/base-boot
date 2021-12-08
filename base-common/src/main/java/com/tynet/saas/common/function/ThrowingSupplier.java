package com.tynet.saas.common.function;

import com.tynet.saas.common.util.ExceptionUtil;

import java.util.function.Supplier;

/**
 * 自定义获取结果的功能接口 - A throwing {@link Supplier}
 *
 * @param <T> 参数类型
 * @author Created by 思伟 on 2021/11/3
 */
@FunctionalInterface
public interface ThrowingSupplier<T> extends Supplier<T> {

    /**
     * 获取结果，可能引发异常。
     *
     * @return a result
     * @throws Exception in case of errors
     */
    T doGet() throws Exception;

    @Override
    default T get() {
        try {
            return this.doGet();
        } catch (Exception e) {
            // 处理异常
            return this.handleException(e);
        }
    }

    /**
     * 处理异常
     * <p>
     * Implement your own exception handling logic here..
     * </p>
     *
     * @param e 异常对象
     * @return a result or throw exception
     */
    default T handleException(Exception e) {
        // 适配抛出运行时异常
        throw ExceptionUtil.wrapRuntimeException(e);
    }

}
