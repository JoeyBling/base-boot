package com.tynet.saas.common.service;

/**
 * 自定义装饰器接口
 * <p>
 * 所有装饰器接口必须实现此类，动态扩展一个类或接口的功能
 * </p>
 *
 * @author Created by 思伟 on 2021/3/23
 */
public interface IDecoratorAble<T> {

    /**
     * 返回此装饰器委托的真实目标对象.
     *
     * @return {@link T}
     */
    T getTarget();

}
