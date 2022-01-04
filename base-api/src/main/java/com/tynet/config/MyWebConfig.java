package com.tynet.config;

import com.tynet.frame.core.web.MyErrorAttributes;
import com.tynet.saas.common.hessian.IAppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义Web配置
 *
 * @author Created by 思伟 on 2022/1/4
 */
@Configuration
public class MyWebConfig {

    @Autowired
    protected IAppProperties appProperties;

    @Bean
    @ConditionalOnMissingBean
    public ErrorAttributes errorAttributes() {
        return new MyErrorAttributes(appProperties);
    }

}
