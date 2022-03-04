package com.tynet.saas.common.service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
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

    /**
     * 转{@link Duration}
     *
     * @return the duration of this unit
     */
    default Duration toDuration() {
        // 时间量
        final long timeQuantity = this.time();
        switch (this.unit()) {
            case NANOSECONDS:
                return Duration.ofNanos(timeQuantity);
            case MICROSECONDS:
                /**
                 * {@link java.util.concurrent.TimeUnit#toChronoUnit} - @since 9
                 */
                return Duration.of(timeQuantity, ChronoUnit.MICROS);
            case MILLISECONDS:
                return Duration.ofMillis(timeQuantity);
            case SECONDS:
                return Duration.ofSeconds(timeQuantity);
            case MINUTES:
                return Duration.ofMinutes(timeQuantity);
            case HOURS:
                return Duration.ofHours(timeQuantity);
            case DAYS:
                return Duration.ofDays(timeQuantity);
            default:
                throw new UnsupportedOperationException("未处理的时间单位：" + this.unit());
        }
    }

}
