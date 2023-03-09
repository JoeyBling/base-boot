package com.tynet.saas.common.service.decorator;

import com.tynet.saas.common.service.IDecoratorAble;
import com.tynet.saas.common.service.MyHandlerInterceptor;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于{@link MyHandlerInterceptor}抽象装饰器实现
 * <p>
 * 简化了真实装饰器类的写法，默认调用实现
 * </p>
 *
 * @author Created by 思伟 on 2021/3/29
 */
public abstract class AbstractMyHandlerInterceptorDecorator
        extends AbstractHandlerInterceptorDecorator<MyHandlerInterceptor>
        implements IDecoratorAble<MyHandlerInterceptor>, MyHandlerInterceptor, AsyncHandlerInterceptor {

    /**
     * one public constructor
     *
     * @param target 要装饰的目标对象
     */
    public AbstractMyHandlerInterceptorDecorator(MyHandlerInterceptor target) {
        super(target);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response,
                                               Object handler) throws Exception {
        this.target.afterConcurrentHandlingStarted(request, response, handler);
    }

    @Override
    public String[] getPath() {
        return this.target.getPath();
    }

    @Override
    public String[] getExcludePath() {
        return this.target.getExcludePath();
    }

}
