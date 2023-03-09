package com.tynet.saas.common.service.adapter;

import com.tynet.saas.common.service.MyHandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;

/**
 * 允许带有空方法的接口实现 <p>子类只重写他们感兴趣的方法
 *
 * @author Created by 思伟 on 2021/3/29
 */
public abstract class MyHandlerInterceptorAdapter implements MyHandlerInterceptor, InitializingBean {
    /**
     * notes：add getLogger method ?
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String toString() {
        return String.format("%s{path=%s, excludePath=%s}", this.getClass().getSimpleName(),
                Arrays.toString(this.getPath()), Arrays.toString(this.getExcludePath()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("自定义拦截器[{}]初始化完成...", this.toString());
        }
    }

}
