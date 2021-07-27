package com.tynet.saas.common.service.impl;

import com.tynet.saas.common.service.IResponse;
import lombok.Getter;

/**
 * 简单响应接口实现
 *
 * @author Created by 思伟 on 2021/3/22
 */
public class SimpleResponse<T> implements IResponse<T> {
    private static final long serialVersionUID = 1L;

    /**
     * 成功响应
     *
     * @param data 响应数据
     * @param <R>  {@link R}
     * @return #self
     */
    public static <R> SimpleResponse<R> success(R data) {
        return new SimpleResponse<>(true, null, data);
    }

    /**
     * 错误响应
     *
     * @param errMsg 错误消息
     * @param data   响应数据
     * @param <R>    {@link R}
     * @return #self
     */
    public static <R> SimpleResponse<R> error(String errMsg, R data) {
        return new SimpleResponse<>(false, errMsg, data);
    }

    /**
     * one public constructor
     *
     * @param success 是否成功
     * @param errMsg  错误消息
     * @param data    响应数据
     */
    public SimpleResponse(Boolean success, String errMsg, T data) {
        this.success = success;
        this.errMsg = errMsg;
        this.data = data;
    }

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误消息
     */
    @Getter
    private String errMsg;

    /**
     * 响应数据
     */
    @Getter
    private T data;

    @Override
    public boolean isSuccess() {
        return Boolean.TRUE.equals(this.success);
    }

    @Override
    public String toString() {
        return "SimpleResponse{" +
                "success=" + success +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
