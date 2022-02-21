package com.tynet.saas.common.service;

import java.util.Date;

/**
 * 时间范围接口
 *
 * @author Created by 思伟 on 2022/2/15
 */
public interface ITimeRange {

    /**
     * 开始时间
     *
     * @return {@link Date}
     */
    Date startTime();

    /**
     * 结束时间
     *
     * @return {@link Date}
     */
    Date endTime();

    /**
     * 空方法的{@link ITimeRange}实现
     * <p>
     * 子类只重写他们感兴趣的方法
     * </p>
     */
    class Empty implements ITimeRange {
        /**
         * 当前实例对象
         */
        private static final Empty INSTANCE = new Empty();

        /**
         * 获取唯一实例对象
         */
        public static final Empty getInstance() {
            return INSTANCE;
        }

        /**
         * {@inheritDoc}
         * <p>This implementation is empty.
         */
        @Override
        public Date startTime() {
            return null;
        }

        /**
         * {@inheritDoc}
         * <p>This implementation is empty.
         */
        @Override
        public Date endTime() {
            return null;
        }

    }

}
