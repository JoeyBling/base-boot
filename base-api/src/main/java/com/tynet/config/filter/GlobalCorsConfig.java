package com.tynet.config.filter;

import com.tynet.frame.prjext.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局CORS跨域请求安全配置
 * <p>
 * {@link ConditionalOnBean} - 当给定的在bean存在时，则实例化当前Bean
 * {@link ConditionalOnMissingBean} - 当给定的在bean不存在时，则实例化当前Bean
 * {@link ConditionalOnClass} - 当给定的类名在类路径上存在，则实例化当前Bean
 * {@link ConditionalOnMissingClass} - 当给定的类名在类路径上不存在，则实例化当前Bean
 * </p>
 *
 * @author Created by 思伟 on 2019/12/25
 */
@Configuration
public class GlobalCorsConfig {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 默认配置
     * <p>
     * {@link org.springframework.web.bind.annotation.CrossOrigin} - 某一接口配置
     * </p>
     *
     * @param applicationProperties 程序配置
     * @return {@code Filter}
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnMissingClass({"com.tynet.config.filter.MyCorsFilter"})
    public CorsFilter corsFilter(ApplicationProperties applicationProperties) {
        // CORS跨域配置
        final List<ApplicationProperties.Mvc.Cors> corsList = applicationProperties.getMvc().getCors();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration;
        for (ApplicationProperties.Mvc.Cors cors : corsList) {
            corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(cors.getAllowedOrigins());
            corsConfiguration.setAllowedHeaders(cors.getAllowedHeaders());
            corsConfiguration.setAllowedMethods(cors.getAllowedMethods()
                    .stream().map(Enum::name).collect(Collectors.toList()));
            // 设置凭证
            corsConfiguration.setAllowCredentials(cors.getAllowCredentials());
            // 响应超时
            corsConfiguration.setMaxAge(cors.getMaxAge());
            // 应用默认设置
            corsConfiguration.applyPermitDefaultValues();
            source.registerCorsConfiguration(cors.getPathPattern(), corsConfiguration);
            logger.info("跨域拦截配置init success - [{}]", cors);
        }
        return new CorsFilter(source);
    }

}
