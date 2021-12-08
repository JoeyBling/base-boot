package com.tynet.saas.common.service.impl;

import com.tynet.saas.common.service.MyInitializingBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 单例模式bean初始化后操作(线程安全)
 * TODO 抽离装饰器
 *
 * @author Created by 思伟 on 2020/10/29
 */
public abstract class SingletonInitializingBean implements MyInitializingBean {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化标识
     */
    protected final AtomicBoolean initFlag = new AtomicBoolean(false);

    /**
     * 是否已初始化完成
     * <p>
     * 暂时不开放被子类重写
     * </p>
     *
     * @return boolean
     */
    public final boolean isInitialized() {
        return Boolean.TRUE.equals(initFlag.get());
    }

    /**
     * 标识初始化成功
     */
    protected final void initSuccess() {
        initFlag.set(true);
    }

    @Override
    public final void init() throws Exception {
        if (!isInitialized()) {
            synchronized (this) {
                if (!isInitialized()) {
                    logger.debug("[{}]-init...", this.getClass().getName());
                    try {
                        this.singletonInit();
                        this.initSuccess();
                    } finally {
                        // 初始化完成后操作
                        this.afterInitialized();
                    }
                }
            }
        }
    }

    /**
     * 单例模式初始化操作
     */
    protected abstract void singletonInit();

    /**
     * 初始化完成后操作
     *
     * @see #singletonInit()
     * @deprecated 不建议调用，将来会弃用
     */
    @Deprecated
    protected void afterInitialized() {
    }

}
