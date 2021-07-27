package com.tynet.saas.common.service.decorator;

import com.tynet.saas.common.service.IDecoratorAble;
import com.tynet.saas.common.service.IResponse;
import com.tynet.saas.common.service.impl.SimpleResponse;

/**
 * 基于{@link IResponse}简单装饰器实现
 *
 * @author Created by 思伟 on 2021/3/22
 */
public class SimpleResponseDecorator<T> extends AbstractResponseDecorator<T> {
    private static final long serialVersionUID = 1L;

    /**
     * one public constructor
     *
     * @param target 要装饰的目标对象
     */
    public SimpleResponseDecorator(IResponse<T> target) {
        super(target);
    }

    /**
     * just test
     */
    public static void main(String[] args) {
        AbstractResponseDecorator<Integer> responseDecorator = new SimpleResponseDecorator<>(SimpleResponse.success(0));
        System.out.println(responseDecorator.getClass());
        // final IResponse<Integer> target = new SimpleResponseDecorator<>(responseDecorator).getTarget();
        // final IResponse<Integer> target = new SimpleResponseDecorator<>(new SimpleResponseDecorator<>(responseDecorator)).getTarget();
        // final IResponse<Integer> target = new LoggingResponseDecorator<>(new LoggingResponseDecorator<>(new SimpleResponseDecorator<>(responseDecorator))).getTarget();
        final IResponse<Integer> target = new SimpleResponseDecorator<>(new LoggingResponseDecorator<>(new LoggingResponseDecorator<>(responseDecorator))).getTarget();
        System.out.println(target.getClass());
        System.out.println(new SimpleResponseDecorator<>(new LoggingResponseDecorator<>(responseDecorator)).isSuccess());
        System.out.println(new SimpleResponseDecorator<>(
                new LoggingResponseDecorator<>(SimpleResponse.success("succ"))).getTarget());

        final IDecoratorAble decorator = new AbstractDecorator<Integer>(1) {
            @Override
            public Integer getTarget() {
                return super.getTarget();
            }
        };
        System.out.println(decorator.getTarget());

        /**
         * 必须保证装饰器对象为一致
         */
        final IDecoratorAble decorator2 = new AbstractDecorator(decorator) {
            @Override
            public Object getTarget() {
                return super.getTarget();
            }
        };

        System.out.println(decorator2.getTarget());

        /**
         * 必须保证装饰器对象为一致
         */
        final IDecoratorAble decorator1 = new AbstractDecorator<IDecoratorAble>(decorator) {
            @Override
            public IDecoratorAble getTarget() {
                return super.getTarget();
            }
        };

        System.out.println(decorator1.getTarget());

    }

}
