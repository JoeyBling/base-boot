package com.tynet.saas.common.util;

import com.tynet.saas.common.exception.MyException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 异常工具类
 *
 * @author Created by 思伟 on 2021/8/19
 */
public class ExceptionUtil {

    /**
     * 获取系统定义的异常错误码
     * <p>
     * 只处理系统自定义的异常
     * </p>
     *
     * @param e 异常对象
     * @return 错误码
     */
    @Nullable
    public static String getErrorCode(@NotNull final Throwable e) {
        // 抛出的目标异常
        final Throwable targetException = ReflectionUtils.getRealThrowable(e);
        if (MyException.class.isAssignableFrom(targetException.getClass())) {
            // 自定义异常
            return ((MyException) targetException).getErrorCode();
        } else {
            System.err.println(String.format("未处理的异常对象：%s", e.getClass().getName()));
        }
        return null;
    }

    /**
     * 使用运行时异常包装编译异常
     *
     * @param e 异常对象
     * @return 运行时异常
     */
    public static RuntimeException wrapRuntimeException(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException(e);
    }

}
