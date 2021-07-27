package com.tynet.saas.common.service;

import java.io.Serializable;

/**
 * 基础响应接口
 *
 * @param <T> 响应数据对象
 * @author Created by 思伟 on 2021/3/2
 */
public interface IResponse<T> extends Serializable {

    /**
     * 是否响应成功
     *
     * @return boolean
     */
    boolean isSuccess();

    /**
     * 获取错误消息
     *
     * @return 当 {@code #isSuccess()==false}时不为空
     */
    String getErrMsg();

    /**
     * 获取响应数据
     *
     * @return {@link T}
     */
    T getData();

}
