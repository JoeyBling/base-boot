package com.tynet.saas.common.context.event;

import com.tynet.saas.common.util.DateUtils;

import java.time.Clock;
import java.util.EventObject;

/**
 * 自定义事件(状态)对象
 *
 * @author Created by 思伟 on 2022/1/26
 */
public class MyEventObject extends EventObject {
    private static final long serialVersionUID = 1L;

    /**
     * 事件发生时的时间戳
     */
    protected final long timestamp;

    /**
     * default constructor
     *
     * @param source 事件源对象
     */
    public MyEventObject(Object source) {
        this(source, DateUtils.currentTimestamp());
    }

    /**
     * extend constructor
     * <p>此函数通常用于测试场景...
     *
     * @param source 事件源对象
     * @param clock  时钟
     */
    public MyEventObject(Object source, Clock clock) {
        this(source, clock.millis());
    }

    /**
     * extend constructor
     *
     * @param source    事件源对象
     * @param timestamp 时间戳
     */
    public MyEventObject(Object source, long timestamp) {
        super(source);
        this.timestamp = timestamp;
    }

    /**
     * 获取事件发生时的时间戳
     *
     * @return long
     */
    public final long getTimestamp() {
        return this.timestamp;
    }

}
