package com.tynet.saas.common.exception;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * 自定义异常接口
 * {@link Throwable}
 *
 * @author Created by 思伟 on 2020/4/10
 */
public interface MyException extends Serializable {

    /**
     * 默认异常错误码
     */
    String DEFAULT_ERROR_CODE = "500";

    /**
     * 获取错误码
     * <p>
     * Defaults to {@link #DEFAULT_ERROR_CODE}.
     * use string type than int
     * 阿里巴巴规范：
     * 1. 错误码为字符串类型
     * 2. 错误产生来源+四位数字编号
     * </p>
     *
     * @return String
     * @since 1.8
     */
    default String getErrorCode() {
        return DEFAULT_ERROR_CODE;
    }

    /**
     * 获取错误信息
     *
     * @return String
     */
    String getMsg();

    /**
     * 获取异常对象
     * <p>
     * 理论上不可为空
     * use {@code Exception} than {@code Throwable} ?
     * 因为{@link Error}表示严重的问题，异常不应该被捕获
     * </p>
     *
     * @return Throwable
     */
    @Nullable Throwable getException();

}
