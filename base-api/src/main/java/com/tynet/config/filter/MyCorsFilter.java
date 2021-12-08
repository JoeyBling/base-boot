package com.tynet.config.filter;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 自定义CORS跨域请求安全配置
 * <p>
 * {@link Order} - 标识执行顺序 值越小越先执行
 * </p>
 *
 * @author Created by 思伟 on 2019/12/17
 */
@Order(1)
@WebFilter(initParams = {
}, filterName = "myCorsFilter", urlPatterns = {
        "/*",
})
public class MyCorsFilter implements Filter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Assert.notNull(filterConfig, "FilterConfig must not be null");
        // 允许跨域访问的域名
        allowOrigins = StringUtils.split(System.getProperty("access.control.allow.origin"), ",");
        // 允许跨域的请求头
        allowHeaders = StringUtils.defaultIfEmpty(
                System.getProperty("access.control.allow.headers"), "Content-Type, sign");
        // 允许跨域的请求方法
        allowMethods = StringUtils.defaultIfEmpty(
                System.getProperty("access.control.allow.methods"),
                org.springframework.util.StringUtils.collectionToCommaDelimitedString(DEFAULT_ALLOW_METHODS)
        );

        /**
         * 是否支持用户凭据
         * <p>
         * {@link BooleanUtils#toBooleanObject(String)} - may return {@code null}
         * {@link BooleanUtils#toBoolean(String)} NOT {@code null}
         * </p>
         */
        allowCredentials = BooleanUtils.toBoolean(System.getProperty("access.control.allow.credentials"));

        // 请求的响应时间 - 单位：秒
        maxAge = StringUtils.defaultIfEmpty(
                System.getProperty("access.control.max.age"), "3600");
        logger.info("WebFilter->[{}] init success...", filterConfig.getFilterName());
        logger.info("初始化CORS跨域请求安全配置完成===={}", this.toString());
    }

    /**
     * 只有跨域请求，或者同域时发送post请求，才会携带origin请求头
     * 而referer不论何种情况下，只要浏览器能获取到请求源都会携带
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            final HttpServletResponse httpResponse = (HttpServletResponse) response;
            // 默认
            String allowOrigin = "*";
            // WEB服务器的域名/IP、地址和端口号
            String reqHost = ((HttpServletRequest) request).getHeader(HttpHeaders.HOST);
            // 包括协议和域名
            String reqOrigin = ((HttpServletRequest) request).getHeader(HttpHeaders.ORIGIN);
            // 请求的原始资源的URI
            String reqReferer = ((HttpServletRequest) request).getHeader(HttpHeaders.REFERER);
            // String requestURL = ((HttpServletRequest) request).getRequestURL().toString();

            if (ArrayUtils.isNotEmpty(allowOrigins)) {
                // 默认使用下标为0的过滤规则
                allowOrigin = allowOrigins[0];
                for (int i = 0; i < allowOrigins.length; i++) {
                    if ("*".equals(allowOrigins[i])) {
                        allowOrigin = reqOrigin;
                        break;
                    } else if (StringUtils.startsWith(reqOrigin, allowOrigins[i])
                            || StringUtils.startsWith(reqReferer, allowOrigins[i])) {
                        allowOrigin = allowOrigins[i];
                        break;
                    }
                }
            }

            httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, allowOrigin);
            httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, allowHeaders);
            httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, allowMethods);
            httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,
                    Boolean.toString(Boolean.TRUE.equals(allowCredentials)));
            httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, maxAge);
            // 页面只能被本站页面嵌入到iframe或者frame中(造成跨域iframe) - X-Frame-Options
            httpResponse.setHeader(com.google.common.net.HttpHeaders.X_FRAME_OPTIONS, "SAMEORIGIN");
        }

        chain.doFilter(request, response);
    }

    /**
     * 允许跨域访问的域名
     */
    private String[] allowOrigins;
    /**
     * 允许跨域的请求头
     */
    private String allowHeaders;
    /**
     * 允许跨域的请求方法
     */
    private String allowMethods;
    /**
     * 是否支持用户凭据
     */
    @Nullable
    private Boolean allowCredentials;
    /**
     * 请求的响应时间 - 单位：秒
     */
    private String maxAge;

    /**
     * 默认允许跨域的请求方法
     */
    public static final List<HttpMethod> DEFAULT_ALLOW_METHODS = Collections.unmodifiableList(
            Arrays.asList(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS));

}
