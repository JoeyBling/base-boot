package com.tynet.saas.common.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.DruidPasswordCallback;
import com.tynet.saas.common.service.ICipherService;
import com.tynet.saas.common.service.impl.NoOpCipherService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Properties;

/**
 * 数据库密码回调解密
 *
 * @author Created by 思伟 on 2019/11/7
 */
@NoArgsConstructor
public class MyDruidPasswordCallback extends DruidPasswordCallback {
    private static final long serialVersionUID = 1L;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 加解密接口
     */
    @Setter
    private ICipherService cipherService = NoOpCipherService.getInstance();

    /**
     * 做个缓存，防止一直请求
     */
    private volatile String password = null;

    /**
     * default constructor
     *
     * @param cipherService {@literal impl}.
     */
    public MyDruidPasswordCallback(@NotNull ICipherService cipherService) {
        this.cipherService = Objects.requireNonNull(cipherService);
    }

    @Override
    public void clearPassword() {
        super.clearPassword();
    }

    @Override
    public void setUrl(String url) {
        super.setUrl(url);
    }

    @Override
    public void setProperties(Properties properties) {
        if (StringUtils.isNotEmpty(password)) {
            // 程序应只在启动时调用密码解密，之后保存在内存中，不能每次使用都调用接口获取密码
            this.setPassword(password.toCharArray());
            return;
        }
        Assert.notNull(properties, "Properties must not be null");
        super.setProperties(properties);

        // 密文
        password = properties.getProperty("password");
        if (StringUtils.isNotBlank(password)) {
            try {
                // 解密
                password = cipherService.decrypt(StrUtil.utf8Bytes(password)).toString();
                this.setPassword(password.toCharArray());
            } catch (Exception e) {
                // 报错了不做异常抛出，有可能是本地测试密码不需要解密
                logger.warn("数据库密文解密失败：{}", e.getMessage(), e);
                this.setPassword(password.toCharArray());
            }
        }
    }

}
