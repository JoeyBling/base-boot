package com.tynet.saas.common.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Optional;
import java.util.function.Function;

/**
 * 反射处理工具类
 *
 * @author Created by 思伟 on 2021/6/4
 */
public abstract class ReflectionUtils extends org.springframework.util.ReflectionUtils {

    /**
     * 获取真实目标异常函数
     */
    public static final Function<Throwable, Throwable> GET_REAL_THROWABLE_FUNCTION = throwable -> {
        // 未声明的检查异常
        if (UndeclaredThrowableException.class.isAssignableFrom(throwable.getClass())) {
            return Optional.ofNullable(((UndeclaredThrowableException) throwable).getUndeclaredThrowable()).orElse(throwable);
        }
        // 反射异常处理
        if (InvocationTargetException.class.isAssignableFrom(throwable.getClass())) {
            return Optional.ofNullable(((InvocationTargetException) throwable).getTargetException()).orElse(throwable);
        }
        // 默认处理返回自身
        return throwable;
    };

    /**
     * 获取真实目标异常
     * <p>
     * 通过方法进行代理调用，让调用更加具有含义
     * </p>
     *
     * @param e 异常抽象
     * @return 真实目标异常
     */
    public static @NotNull Throwable getRealThrowable(@NotNull Throwable e) {
        return GET_REAL_THROWABLE_FUNCTION.apply(e);
    }

}
