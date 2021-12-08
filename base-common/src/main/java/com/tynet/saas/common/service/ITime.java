package com.tynet.saas.common.service;

import java.util.concurrent.TimeUnit;

/**
 * 时间接口
 *
 * @author Created by 思伟 on 2021/9/9
 */
public interface ITime {

    /**
     * 时间
     *
     * @return long
     */
    long time();

    /**
     * 时间单位
     *
     * @return {@link TimeUnit}
     */
    TimeUnit unit();

}
