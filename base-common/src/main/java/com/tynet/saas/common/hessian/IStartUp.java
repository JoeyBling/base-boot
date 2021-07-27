package com.tynet.saas.common.hessian;

import com.tynet.saas.common.service.ISortAble;

/**
 * 程序启动&初始化完成回调接口
 * <p>
 * 一般用于完成初始化工作
 * 在系统启动时，会调用实现此接口的类
 * 注意实现此接口的类，必须被spring管理
 * {@code @FunctionalInterface} 是在 Java 8 中添加的一个新注解，用于指示接口类型，声明接口为 Java 语言规范定义的功能接口
 * 一旦定义了功能接口，我们就可以利用 `Lambda` 表达式调用
 * </p>
 *
 * @author Created by 思伟 on 2020/4/23
 */
@FunctionalInterface
public interface IStartUp extends ISortAble {

    /**
     * 初始化启动
     *
     * @throws Exception
     */
    void startUp() throws Exception;

}
