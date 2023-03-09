package com.tynet.saas.common.service.decorator;

import com.tynet.saas.common.service.MyHandlerInterceptor;

/**
 * 基于{@link MyHandlerInterceptor}简单装饰器实现
 *
 * @author Created by 思伟 on 2021/3/29
 */
public class SimpleMyHandlerInterceptorDecorator
        extends AbstractMyHandlerInterceptorDecorator {

    /**
     * one public constructor
     *
     * @param target 要装饰的目标对象
     */
    public SimpleMyHandlerInterceptorDecorator(MyHandlerInterceptor target) {
        super(target);
    }

}
