package com.tynet.saas.common.service.decorator;

import com.tynet.saas.common.service.IDecoratorAble;
import com.tynet.saas.common.service.IResponse;

/**
 * 基于{@link IResponse}抽象装饰器实现
 * <p>
 * 简化了真实装饰器类的写法，默认调用实现
 * </p>
 *
 * @author Created by 思伟 on 2021/3/22
 */
public abstract class AbstractResponseDecorator<T>
        extends AbstractDecorator<IResponse<T>>
        implements IDecoratorAble<IResponse<T>>, IResponse<T> {
    private static final long serialVersionUID = 1L;

    /**
     * one public constructor
     *
     * @param target 要装饰的目标对象
     */
    public AbstractResponseDecorator(IResponse<T> target) {
        super(target);
    }

    @Override
    public boolean isSuccess() {
        return this.target.isSuccess();
    }

    @Override
    public String getErrMsg() {
        return this.target.getErrMsg();
    }

    @Override
    public T getData() {
        return this.target.getData();
    }

}
