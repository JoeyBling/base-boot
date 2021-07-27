package com.tynet.saas.common.service;

import com.tynet.saas.common.service.annotation.ApiMethod;

/**
 * Api接口声明
 * <p>
 * 目前暂不实现动态代理手动注册Spring容器bean
 * </p>
 *
 * @author Created by 思伟 on 2020/8/3
 */
public interface ApiService {

    /**
     * 整个接口类默认的调用权限
     * <p>Defaults {@link ApiMethod#permission()}
     *
     * @return {@code String} array.
     */
    default String[] getPermission() {
        return null;
    }

}
