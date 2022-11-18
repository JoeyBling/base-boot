package com.tynet.saas.common.service.decorator;

import com.tynet.saas.common.service.MyHandlerInterceptor;

/**
 * Shiro简单日志简单装饰器实现
 *
 * @author Created by 思伟 on 2021/3/29
 */
public class ShiroMyHandlerInterceptorDecorator extends AbstractMyHandlerInterceptorDecorator {
    private static final long serialVersionUID = 1L;

    /**
     * one public constructor
     *
     * @param target 要装饰的目标对象
     */
    public ShiroMyHandlerInterceptorDecorator(MyHandlerInterceptor target) {
        super(target);
    }


}
