package com.tynet.saas.common.service.impl;

import com.tynet.saas.common.service.ITimeRange;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 简单{@link ITimeRange}实现
 *
 * @author Created by 思伟 on 2021/9/9
 */
@Data
@AllArgsConstructor
public class SimpleTimeRange implements ITimeRange {

    /**
     * 开始时间
     */
    private final Date startTime;

    /**
     * 结束时间
     */
    private final Date endTime;

    @Override
    public Date startTime() {
        return startTime;
    }

    @Override
    public Date endTime() {
        return endTime;
    }

}
