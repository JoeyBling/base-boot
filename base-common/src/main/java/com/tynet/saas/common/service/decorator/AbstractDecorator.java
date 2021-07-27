package com.tynet.saas.common.service.decorator;

import com.tynet.saas.common.service.IDecoratorAble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 公用简单抽象装饰器声明实现
 * <p>
 * </p>
 *
 * @author Created by 思伟 on 2021/3/23
 */
public abstract class AbstractDecorator<T> implements IDecoratorAble<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 目标对象
     * <p>
     * use {@code final} instead of @code volatile} ?
     * </p>
     */
    // protected volatile T target;
    protected final T target;

    /**
     * one public constructor
     *
     * @param target 要装饰的目标对象
     */
    public AbstractDecorator(T target) {
        this.target = Objects.requireNonNull(target);
    }

    /**
     * 返回此装饰器委托的真实目标对象.
     * <p>
     * 如果目标对象是自身，则递归获取真实目标对象
     * </p>
     *
     * @return {@link #target}
     */
    @Override
    public T getTarget() {
        // return target;
        return target instanceof IDecoratorAble ? ((IDecoratorAble<T>) target).getTarget() : target;
        // return target instanceof AbstractDecorator ? ((AbstractDecorator<T>) target).getTarget() : target;
        // return target instanceof IDecoratorAble ? (T) ((IDecoratorAble) target).getTarget() : target;
    }
}
