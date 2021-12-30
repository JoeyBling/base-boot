package com.tynet.saas.common.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.tynet.saas.common.service.ByteWrapper;
import com.tynet.saas.common.service.ICipherService;
import org.jetbrains.annotations.NotNull;

/**
 * MD5算法加解密接口实现
 *
 * @author Created by 思伟 on 2021/12/27
 */
public class MD5CipherService implements ICipherService {
    /**
     * 算法名称
     */
    public static final String ALGORITHM_NAME = "MD5";

    /**
     * 获取基础提供程序
     *
     * @return {@literal Object}
     */
    public @NotNull MD5 getNativeProvider() {
        // 非线程安全 - {@literal prototype scope}
        return SecureUtil.md5();
    }

    @Override
    public ByteWrapper encrypt(byte[] raw) {
        return ByteWrappers.bytes(this.getNativeProvider().digest(raw));
    }

    @Override
    public ByteWrapper decrypt(byte[] encrypted) {
        // 摘要加密算法不可逆 - 请求的操作不受支持
        throw new UnsupportedOperationException("暂不支持...");
    }

    /**
     * just test
     */
    public static void main(String[] args) {
        final ICipherService cipherService = new MD5CipherService();
        final ByteWrapper byteWrapper = cipherService.encrypt("test");
        System.out.println(byteWrapper.toHex());
    }

}
