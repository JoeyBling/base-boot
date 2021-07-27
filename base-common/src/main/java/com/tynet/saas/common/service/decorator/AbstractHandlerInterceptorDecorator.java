package com.tynet.saas.common.service.decorator;

import com.tynet.saas.common.service.IDecoratorAble;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于{@link HandlerInterceptor}抽象装饰器实现
 * <p>
 * 简化了真实装饰器类的写法，默认调用实现
 * </p>
 *
 * @author Created by 思伟 on 2021/3/29
 */
public abstract class AbstractHandlerInterceptorDecorator<I extends HandlerInterceptor>
        extends AbstractDecorator<I>
        implements IDecoratorAble<I>, HandlerInterceptor {

    /**
     * one public constructor
     *
     * @param target 要装饰的目标对象
     */
    public AbstractHandlerInterceptorDecorator(I target) {
        super(target);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return this.target.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        this.target.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        this.target.afterCompletion(request, response, handler, ex);
    }
}
