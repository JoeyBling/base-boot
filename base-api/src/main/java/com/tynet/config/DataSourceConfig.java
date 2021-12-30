package com.tynet.config;

import com.alibaba.druid.util.DruidPasswordCallback;
import com.tynet.saas.common.service.ICipherService;
import com.tynet.saas.common.util.MyDruidPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 数据源配置
 *
 * @author Created by 思伟 on 2021/12/29
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class DataSourceConfig {

    /**
     * 数据库密码回调解密
     * <p>
     * 参考：https://github.com/alibaba/druid/pull/3877
     * </p>
     *
     * @return {@link javax.security.auth.callback.PasswordCallback}
     */
    @Bean
    @ConditionalOnMissingBean
    public DruidPasswordCallback myDruidPasswordCallback(
            @Autowired(required = false) ICipherService druidCipherService) {
        final MyDruidPasswordCallback passwordCallback = new MyDruidPasswordCallback();
        if (null != druidCipherService) {
            passwordCallback.setCipherService(druidCipherService);
        }
        return passwordCallback;
    }

}
