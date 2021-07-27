package com.tynet.saas.common.service;

import com.tynet.saas.common.hessian.IStartUp;
import org.springframework.beans.factory.InitializingBean;

/**
 * 在bean初始化完成后执行自定义操作,或只是检查是否已设置所有必需属性
 * <p>
 * 还要改进
 * 1. 建议不实现{@link InitializingBean} 参考 {@link IStartUp}
 * 2. 可以自定义 {@link org.springframework.beans.factory.config.BeanPostProcessor} 来处理
 * </p>
 *
 * @author Created by 思伟 on 2020/10/29
 */
@FunctionalInterface
public interface MyInitializingBean extends InitializingBean {

    /**
     * 集成Spring
     *
     * @throws Exception
     */
    @Override
    default void afterPropertiesSet() throws Exception {
        this.init();
    }

    /**
     * 执行初始化操作
     *
     * @throws Exception
     */
    void init() throws Exception;

}
