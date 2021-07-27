package com.tynet.saas.common.handler;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ClassUtil;
import com.tynet.saas.common.constant.ResponseCodeConst;
import com.tynet.saas.common.exception.SysRuntimeException;
import com.tynet.saas.common.hessian.IStartUp;
import com.tynet.saas.common.service.ApiService;
import com.tynet.saas.common.service.annotation.ApiMethod;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 接口扫描注册类
 * <p>
 * </p>
 *
 * @author Created by 思伟 on 2021/5/17
 */
public abstract class ApiServiceInitService implements IStartUp {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Api接口集合
     */
    private static final Map<String, ApiMethodHandler> API_METHOD_MAP =
            new ConcurrentHashMap<String, ApiMethodHandler>(MapUtil.DEFAULT_INITIAL_CAPACITY);

    /**
     * 返回只读信息
     *
     * @return Map
     */
    public final static Map<String, ApiMethodHandler> getApiMethodMap() {
        return Collections.unmodifiableMap(API_METHOD_MAP);
    }

    @Override
    public final void startUp() throws Exception {
        for (ApiService apiService : Objects.requireNonNull(this.getApiServiceList())) {
            // 是否需要处理Aop代理对象转目标对象
            ReflectionUtils.doWithMethods(apiService.getClass(), method -> {
                final ApiMethod serviceMethod = AnnotationUtils.findAnnotation(method, ApiMethod.class);
                if (serviceMethod != null) {
                    final ApiMethodHandler handler = new ApiMethodHandler();
                    handler.setHandler(apiService);
                    handler.setHandlerMethod(method);
                    // 获取处理方法的请求对象参数类型
                    handler.setRequestTypes(method.getParameterTypes());
                    handler.setPermission(Optional.ofNullable(serviceMethod.permission())
                            // 接口类默认的调用权限
                            .orElse(apiService.getPermission()));
                    handler.setLogical(serviceMethod.logical());

                    // 校验Api接口是否合法
                    if (!this.validHandler(handler)) {
                        throw new SysRuntimeException(ResponseCodeConst.ERROR_VALIDATE,
                                MessageFormat.format("Api接口错误[{0}]", handler));
                    }

                    if (null == serviceMethod.name() || serviceMethod.name().length < 1) {
                        // 方法名
                        final String methodName = method.getName();
                        logger.warn("[{}]定义的接口名为空，使用默认方法名[{}]",
                                ClassUtil.getClassName(method.getDeclaringClass(), false), methodName);
                        this.registerApiMethod(methodName, handler);
                    } else {
                        for (String serviceName : serviceMethod.name()) {
                            this.registerApiMethod(serviceName, handler);
                        }
                    }
                }
            }, method -> !method.isSynthetic() && null != AnnotationUtils.findAnnotation(method, ApiMethod.class));
        }
    }

    /**
     * 获取需要注册的Api接口声明实现类
     *
     * @return List
     */
    @NotNull
    protected abstract List<ApiService> getApiServiceList();

    /**
     * 校验Api接口处理器包装对象是否有效
     *
     * @param handler 包装对象
     * @return boolean
     */
    protected abstract boolean validHandler(ApiMethodHandler handler);

    /**
     * 注册API接口类
     *
     * @param methodName 接口名
     * @param handler    Api接口处理对象
     */
    public final void registerApiMethod(String methodName, ApiMethodHandler handler) {
        synchronized (API_METHOD_MAP) {
            if (API_METHOD_MAP.containsKey(methodName)) {
                logger.error("重复的API[{}]", methodName);
            } else {
                API_METHOD_MAP.put(methodName, Objects.requireNonNull(handler));
                logger.info("注册API键值：{}", methodName);
            }
        }
    }

}
