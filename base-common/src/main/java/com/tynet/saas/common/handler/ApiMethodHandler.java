package com.tynet.saas.common.handler;

import com.tynet.saas.common.service.annotation.Logical;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * Api接口处理器包装对象
 *
 * @author Created by 思伟 on 2021/5/17
 */
@Data
public class ApiMethodHandler {

    /**
     * 处理器对象
     */
    private Object handler;

    /**
     * 处理器的处理方法
     */
    private Method handlerMethod;

    /**
     * 处理方法的请求对象类
     */
    private Class<?>[] requestTypes = null;

    /**
     * 接口调用权限(可设置多个)
     */
    private String[] permission;

    /**
     * 权限检查的逻辑操作
     */
    private Logical logical;

}
