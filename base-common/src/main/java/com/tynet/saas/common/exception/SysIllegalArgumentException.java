package com.tynet.saas.common.exception;

/**
 * 自定义系统入参错误
 *
 * @author Created by 思伟 on 2020/12/21
 */
public class SysIllegalArgumentException extends BaseRuntimeException {
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
    public SysIllegalArgumentException(String code, String message) {
        this(message);
        this.code = code;
    }

    /**
     * call super
     */
    public SysIllegalArgumentException(String message) {
        super(message);
    }

    /**
     * {@link #code}
     */
    public String getCode() {
        return code;
    }

    /**
     * <p>
     * 是否考虑不对外开放修改
     * </p>
     * {@link #code}
     */
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    protected String errorCode() {
        return this.code;
    }

}
