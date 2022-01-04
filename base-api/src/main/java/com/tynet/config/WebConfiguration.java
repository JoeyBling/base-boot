package com.tynet.config;

import com.google.common.collect.Lists;
import com.tynet.frame.prj.ApplicationProperties;
import com.tynet.saas.common.constant.SystemConst;
import com.tynet.saas.common.hessian.IAppProperties;
import com.tynet.saas.common.interceptor.SimpleLoggingInterceptor;
import com.tynet.saas.common.service.MyHandlerInterceptor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 自定义Web配置
 *
 * @author Updated by 思伟 on 2020/7/1
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 需要提供的静态资源映射配置
     */
    protected final List<ResourceHandlerInnerHelper> resourceHandlerInnerHelpers = Lists.newArrayList();

    @Autowired
    protected IAppProperties appProperties;
    @Autowired
    protected ApplicationProperties applicationProperties;

    /**
     * init serve
     */
    public WebConfiguration() {
    }

    /**
     * 解决String乱码问题
     *
     * @see org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor#writeWithMessageConverters(Object, MethodParameter, ServletServerHttpRequest, ServletServerHttpResponse)
     */
    @Bean
    public HttpMessageConverter<String> defaultStringHttpMessageConverter() {
        /**
         * @see AbstractHttpMessageConverter#getDefaultCharset()
         */
        StringHttpMessageConverter converter = new StringHttpMessageConverter(SystemConst.DEFAULT_CHARSET);
        return converter;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (int i = 0; null != converters && i < converters.size(); i++) {
            HttpMessageConverter<?> httpMessageConverter = converters.get(i);
            if (httpMessageConverter.getClass().equals(StringHttpMessageConverter.class)) {
                converters.set(i, this.defaultStringHttpMessageConverter());
            }
        }
    }

    /**
     * 自定义资源映射（如果存在2层映射资源路径有相同名字，后一个定义的优先）
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 资源处理顺序（集合越前的解析顺序越高）
        int order = 1;
        for (ResourceHandlerInnerHelper resourceHandler :
                Optional.ofNullable(resourceHandlerInnerHelpers).orElse(Collections.emptyList())) {
            // CachePeriod ?
            registry.setOrder(order).addResourceHandler(resourceHandler.getPathPatterns())
                    .addResourceLocations(resourceHandler.getResourceLocations());
            ++order;
            logger.debug("自定义资源映射成功。URL=[{}],Locations=[{}]",
                    ArrayUtils.toString(resourceHandler.getPathPatterns()),
                    ArrayUtils.toString(resourceHandler.getResourceLocations()));
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加配置拦截器 - 换成外部Bean注册 - 内部注入List
        List<MyHandlerInterceptor> interceptorList = Arrays.asList(
                new SimpleLoggingInterceptor()
        );
        for (MyHandlerInterceptor myHandlerInterceptor : interceptorList) {
            registry.addInterceptor(myHandlerInterceptor)
                    .addPathPatterns(myHandlerInterceptor.getPath())
                    .excludePathPatterns(myHandlerInterceptor.getExcludePath());
            if (logger.isDebugEnabled()) {
                logger.debug("自定义拦截器[{}]初始化完成...", myHandlerInterceptor.toString());
            }
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        Optional.ofNullable(this.applicationProperties.getMvc()).ifPresent(mvc -> {
            if (!CollectionUtils.isEmpty(mvc.getViewResolvers())) {
                logger.debug("自定义简单响应自动控制器={}", mvc.getViewResolvers().toString());
                for (ApplicationProperties.Mvc.ViewResolver viewResolver : mvc.getViewResolvers()) {
                    registry.addViewController(viewResolver.getUrlPath())
                            .setViewName(viewResolver.getViewName());
                }
            }
        });
    }

    /**
     * 自定义资源映射内部帮助类
     */
    @Data
    @Accessors(chain = true)
    protected static class ResourceHandlerInnerHelper {
        /**
         * URL路径
         *
         * @see ResourceHandlerRegistry#addResourceHandler(String...)
         */
        private String[] pathPatterns;

        /**
         * 资源位置
         *
         * @see ResourceHandlerRegistration#addResourceLocations(String...)
         */
        private String[] resourceLocations;
    }

}
