package com.tynet.saas.common.service.impl;

import com.tynet.saas.common.service.ITime;

import java.util.concurrent.TimeUnit;

/**
 * 简单时间接口实现
 *
 * @author Created by 思伟 on 2021/9/9
 */
public class SimpleTime implements ITime {

    /**
     * 时间
     */
    private final long time;

    /**
     * 时间单位
     */
    private final TimeUnit unit;

    /**
     * full-constructor
     *
     * @param time 时间
     * @param unit 时间单位
     */
    public SimpleTime(long time, TimeUnit unit) {
        this.time = time;
        this.unit = unit;
    }

    @Override
    public long time() {
        return time;
    }

    @Override
    public TimeUnit unit() {
        return unit;
    }

}
