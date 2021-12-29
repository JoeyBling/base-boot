package com.tynet.saas.common.service;

/**
 * 字节包装器接口
 * <p>
 * 封装字节数组并提供额外的编码操作
 * 大多数使用{@link com.tynet.saas.common.service.impl.ByteWrappers}构造实例
 * </p>
 *
 * @author Created by 思伟 on 2021/12/27
 */
public interface ByteWrapper {

    /**
     * 返回包装的字节数组
     * <p> the wrapped byte array.
     *
     * @return {@code byte} array.
     */
    byte[] getBytes();

    /**
     * 字节数组是否为空
     *
     * @return boolean
     */
    boolean isEmpty();

    /**
     * 转换为十六进制字符串
     *
     * @return String
     */
    String toHex();

    /**
     * 转换为base64字符串
     *
     * @return String
     */
    String toBase64();

}
