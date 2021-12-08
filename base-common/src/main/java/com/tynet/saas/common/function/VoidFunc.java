package com.tynet.saas.common.function;

import com.tynet.saas.common.util.ExceptionUtil;

/**
 * 自定义无参无返回值的功能接口
 * <p>
 * 参考：{@link cn.hutool.core.lang.func.VoidFunc0}
 * {@link Void}
 * </p>
 *
 * @author Created by 思伟 on 2021/8/20
 */
@FunctionalInterface
public interface VoidFunc {

    /**
     * 执行函数
     *
     * @throws Exception in case of errors
     */
    void exec() throws Exception;

    /**
     * 执行函数 - 包装运行时异常
     */
    default void execWithRuntimeException() {
        try {
            this.exec();
        } catch (Exception e) {
            throw ExceptionUtil.wrapRuntimeException(e);
        }
    }

}
