package com.tynet.saas.common.service.impl;

import com.tynet.saas.common.service.ByteWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;

/**
 * {@link ByteWrapper}实例的工厂类
 *
 * @author Created by 思伟 on 2021/12/27
 */
public final class ByteWrappers {

    /**
     * 对象是否可以转换成实例
     *
     * @param source obj
     * @return boolean
     */
    public static boolean isCompatible(Object source) {
        return source instanceof byte[] || source instanceof char[] || source instanceof String ||
                source instanceof ByteWrapper || source instanceof File || source instanceof InputStream;
    }

    /**
     * create a new instance
     *
     * @param bytes {@code byte} array.
     * @return pojo
     */
    public static ByteWrapper bytes(byte[] bytes) {
        return new SimpleByteWrapper(bytes);
    }

    /**
     * create a new instance
     *
     * @param chars {@code char} array.
     * @return pojo
     */
    public static ByteWrapper bytes(char[] chars) {
        return new SimpleByteWrapper(chars);
    }

    /**
     * create a new instance
     *
     * @param string {@code String}.
     * @return pojo
     */
    public static ByteWrapper bytes(String string) {
        return new SimpleByteWrapper(string);
    }

    /**
     * create a new instance
     *
     * @param instance {@literal case}.
     * @return pojo
     */
    public static ByteWrapper bytes(ByteWrapper instance) {
        return new SimpleByteWrapper(instance);
    }

    /**
     * create a new instance
     *
     * @param file {@code File}.
     * @return pojo
     */
    public static ByteWrapper bytes(File file) {
        return new SimpleByteWrapper(file);
    }

    /**
     * create a new instance
     * <p>
     * 此方法不关闭提供的 {@code InputStream}输入流
     * 如果需要，调用者有责任关闭流。
     * </p>
     *
     * @param inputStream {@code InputStream}.
     * @return pojo
     */
    public static ByteWrapper bytes(InputStream inputStream) {
        return new SimpleByteWrapper(inputStream);
    }

    /**
     * create a new instance
     *
     * @param source obj
     * @return pojo
     * @throws IllegalArgumentException
     */
    public static @NotNull ByteWrapper bytes(Object source) throws IllegalArgumentException {
        if (source == null) {
            return null;
        }
        if (!isCompatible(source)) {
            String msg = "无法为类型 [" + source.getClass().getName() + "] 转换成实例...";
            throw new IllegalArgumentException(msg);
        }
        if (source instanceof byte[]) {
            return bytes((byte[]) source);
        } else if (source instanceof char[]) {
            return bytes((char[]) source);
        } else if (source instanceof String) {
            return bytes((String) source);
        } else if (source instanceof ByteWrapper) {
            // 直接返回
            return (ByteWrapper) source;
        } else if (source instanceof File) {
            return bytes((File) source);
        } else if (source instanceof InputStream) {
            return bytes((InputStream) source);
        } else {
            throw new IllegalStateException(String.format("未处理的对象类型：%s", source.getClass().getName()));
        }
    }

    /**
     * 外部不提供实例化方法
     */
    private ByteWrappers() {
    }

}
