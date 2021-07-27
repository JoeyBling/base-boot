package com.tynet.saas.common.exception;

/**
 * 自定义系统运行时异常
 *
 * @author Created by 思伟 on 2021/4/25
 */
public class SysRuntimeException extends BaseRuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误代码
     */
    protected String code;

    /**
     * default constructor
     *
     * @param code    错误代码
     * @param message the detail message.
     */
    public SysRuntimeException(String code, String message) {
        this(message);
        this.code = code;
    }

    /**
     * call super
     */
    public SysRuntimeException(String message) {
        super(message);
    }

    /**
     * {@link #code}
     */
    public String getCode() {
        return code;
    }

    @Override
    protected String errorCode() {
        return this.code;
    }

}
