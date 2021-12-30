package com.tynet.saas.common.service.impl;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import com.tynet.saas.common.service.ByteWrapper;
import com.tynet.saas.common.service.ICipherService;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.Immutable;

/**
 * 一个基本的，没有操作{@link ICipherService}实现
 *
 * @author Created by 思伟 on 2021/12/28
 */
@Immutable
public final class NoOpCipherService implements ICipherService {
    /**
     * 当前实例对象
     */
    private static final NoOpCipherService INSTANCE = new NoOpCipherService();

    /**
     * 获取唯一实例对象
     */
    public static NoOpCipherService getInstance() {
        return INSTANCE;
    }

    /**
     * 获取基础提供程序
     *
     * @return {@literal Object}
     */
    public @NotNull ICipherService getNativeProvider() {
        return this;
    }

    @Override
    public ByteWrapper encrypt(byte[] raw) {
        return ByteWrappers.bytes(raw);
    }

    @Override
    public ByteWrapper decrypt(byte[] encrypted) {
        return ByteWrappers.bytes(encrypted);
    }

    /**
     * 外部不提供实例化方法
     */
    private NoOpCipherService() {
    }

    /**
     * just test
     */
    public static void main(String[] args) throws DecoderException {
        final ICipherService instance = NoOpCipherService.getInstance();
        final ByteWrapper byteWrapper = instance.encrypt("test");

        System.out.println(byteWrapper.toHex() + "---" + Hex.encodeHexString(byteWrapper.getBytes()));
        System.out.println(HexUtil.decodeHexStr(byteWrapper.toHex()) + "---"
                + StrUtil.utf8Str(Hex.decodeHex(byteWrapper.toHex())));

        System.out.println(byteWrapper);

        // 解密
        System.out.println(instance.decrypt(byteWrapper.getBytes()));
        System.out.println(instance.decrypt(byteWrapper.toHex()));
        System.out.println(instance.decrypt(byteWrapper.toBase64()));

        System.out.println(instance.decryptStr(byteWrapper.toString()));
    }

}
