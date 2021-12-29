package com.tynet.saas.common.service.adapter;

import cn.hutool.core.util.HexUtil;
import com.tynet.saas.common.service.ByteWrapper;
import lombok.Getter;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 允许带有空方法的{@link ByteWrapper}实现
 * <p>
 * 子类只重写他们感兴趣的方法
 * </p>
 *
 * @author Created by 思伟 on 2021/12/27
 */
public abstract class ByteWrapperAdapter implements ByteWrapper {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 字节数组
     */
    @Getter
    private final byte[] bytes;

    /**
     * 缓存 - 十六进制字符串
     */
    protected String cachedHex;

    /**
     * 缓存 - base64字符串
     */
    protected String cachedBase64;

    /**
     * default constructor
     *
     * @param bytes {@code byte} array.
     */
    public ByteWrapperAdapter(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public boolean isEmpty() {
        return null == this.bytes || this.bytes.length == 0;
    }

    @Override
    public String toHex() {
        if (null == cachedHex) {
            cachedHex = HexUtil.encodeHexStr(this.getBytes());
        }
        return cachedHex;
    }

    @Override
    public String toBase64() {
        if (null == cachedBase64) {
            cachedBase64 = Base64.encodeBase64String(this.getBytes());
        }
        return cachedBase64;
    }

    @Override
    public String toString() {
        return this.toBase64();
    }

}
