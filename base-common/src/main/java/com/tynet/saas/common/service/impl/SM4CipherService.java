package com.tynet.saas.common.service.impl;

import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import com.tynet.saas.common.service.ByteWrapper;
import com.tynet.saas.common.service.ICipherService;
import lombok.Setter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.jetbrains.annotations.NotNull;

/**
 * SM4国密算法加解密接口实现
 *
 * @author Created by 思伟 on 2021/12/29
 */
public class SM4CipherService implements ICipherService {
    /**
     * 算法名称
     */
    public static final String ALGORITHM_NAME = "SM4";

    /**
     * default constructor
     *
     * @param cipherKey 密钥
     */
    public SM4CipherService(byte[] cipherKey) {
        this.cipherKey = cipherKey;
    }

    /**
     * zero-argument constructor
     */
    public SM4CipherService() {
        // 使用随机密钥 - SM4算法目前只支持128位（即密钥16字节）
        this(KeyUtil.generateKey(SM4.ALGORITHM_NAME, 128).getEncoded());
    }

    /**
     * extend constructor
     *
     * @param cipherKeyStr 密钥Hex（16进制）或Base64表示形式
     */
    public SM4CipherService(String cipherKeyStr) {
        // 解码字符串密钥
        this(SecureUtil.decode(cipherKeyStr));
    }

    /**
     * 密钥 - 加解密使用
     * <p>
     * 不可外泄
     * </p>
     */
    @Setter
    private byte[] cipherKey;

    /**
     * SM4国密算法
     */
    protected volatile SM4 sm4;

    /**
     * 获取算法名称
     *
     * @return String
     */
    public String getAlgorithm() {
        /**
         * {@link java.security.Key#getAlgorithm}
         * {@link javax.crypto.Cipher#getAlgorithm}
         */
        return sm4.getSecretKey().getAlgorithm();
        // return ALGORITHM_NAME;
    }

    /**
     * 获取基础提供程序
     *
     * @return {@literal Object}
     */
    public @NotNull SM4 getNativeProvider() {
        if (null == sm4) {
            synchronized (this) {
                // 双检锁/双重校验锁
                if (null == sm4) {
                    sm4 = SmUtil.sm4(cipherKey);
                }
            }
        }
        return sm4;
    }

    @Override
    public ByteWrapper encrypt(byte[] raw) {
        return ByteWrappers.bytes(this.getNativeProvider().encrypt(raw));
    }

    @Override
    public ByteWrapper decrypt(byte[] encrypted) {
        return ByteWrappers.bytes(this.getNativeProvider().decrypt(encrypted));
    }

    /**
     * just test
     */
    public static void main(String[] args) throws DecoderException {
        // 默认SM4密钥Base64
        final String SM4_BASE64_KEY = "qFVf3ptj05TeWFzmuJyLNw==";
        final ICipherService instance = new SM4CipherService(SM4_BASE64_KEY);
        final ByteWrapper byteWrapper = instance.encrypt("test");

        System.out.println(byteWrapper.toHex() + "---" + Hex.encodeHexString(byteWrapper.getBytes()));

        // 解密
        System.out.println(instance.decrypt(byteWrapper.getBytes()));
        System.out.println(instance.decrypt(byteWrapper.toHex()));
        System.out.println(instance.decrypt(byteWrapper.toBase64()));
    }

}
