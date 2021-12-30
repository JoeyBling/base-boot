package com.tynet.saas.common.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * 加解密接口
 * <p>
 * 加密分为三种：<br>
 * 1、对称加密（symmetric），例如：AES、DES等<br>
 * 2、非对称加密（asymmetric），例如：RSA、DSA等<br>
 * 3、摘要加密（digest），例如：MD5、SHA-1、SHA-256、HMAC等<br>
 * </p>
 *
 * @author Created by 思伟 on 2021/12/27
 */
public interface ICipherService {

    /**
     * 加密数据
     *
     * @param raw {@code byte} array.
     * @return pojo
     */
    ByteWrapper encrypt(byte[] raw);

    /**
     * 加密数据
     *
     * @param plaintext 明文
     * @return pojo
     */
    default ByteWrapper encrypt(String plaintext) {
        return this.encrypt(StrUtil.utf8Bytes(plaintext));
    }

    /**
     * 解密数据
     *
     * @param encrypted {@code byte} array.
     * @return pojo
     */
    ByteWrapper decrypt(byte[] encrypted);

    /**
     * 解密数据
     *
     * @param data Hex（16进制）或Base64表示形式
     * @return pojo
     */
    default ByteWrapper decrypt(String data) {
        // 解码字符串密钥
        return this.decrypt(SecureUtil.decode(data));
    }

    /**
     * 解密数据
     *
     * @param data 待编码字符串
     * @return pojo
     * @deprecated 将来可能会弃用
     */
    @Deprecated
    default ByteWrapper decryptStr(String data) {
        return this.decrypt(StrUtil.utf8Bytes(data));
    }

}
