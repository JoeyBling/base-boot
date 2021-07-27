package com.tynet.saas.common.exception;

import com.tynet.saas.common.util.StringUtils;

/**
 * 自定义异常抽象类
 * <p>
 * 系统中的异常类都要继承自这个父类
 * 运行时异常 {@link RuntimeException}
 * </p>
 *
 * @author Created by 思伟 on 2020/4/10
 */
public abstract class BaseRuntimeException extends RuntimeException implements MyException {
    private static final long serialVersionUID = 1L;

    /**
     * 自定义错误码为空则返回默认错误码
     */
    @Override
    public final String getErrorCode() {
        return StringUtils.defaultIfEmpty(this.errorCode(), MyException.super.getErrorCode());
    }

    /**
     * 获取自定义错误码
     *
     * @return String
     */
    protected String errorCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return super.getMessage();
    }

    @Override
    public Throwable getException() {
        return this;
        // return Optional.ofNullable(this.getCause()).orElse(this);
    }

    /**
     * call super
     */
    public BaseRuntimeException(String message) {
        super(message);
    }

    /**
     * call super
     */
    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * call super
     */
    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

}
