package com.tynet.config.filter;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 防止XSS攻击的过滤器
 * <p>
 * {@link Order} - 标识执行顺序 值越小越先执行
 * </p>
 *
 * @author Created by 思伟 on 2019/12/25
 */
@Order(2)
@WebFilter(initParams = {
        @WebInitParam(name = XssFilter.PARAM_NAME_EXCLUSIONS, value = XssFilter.PARAM_VALUE_EXCLUSIONS),
}, filterName = "xssFilter", urlPatterns = {
        "/*",
})
public class XssFilter implements Filter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Assert.notNull(filterConfig, "FilterConfig must not be null");
        {
            // 普通代码块
            String[] url = StringUtils.split(
                    filterConfig.getInitParameter(PARAM_NAME_EXCLUSIONS), DEFAULT_SEPARATOR_CHARS);
            for (int i = 0; url != null && i < url.length; i++) {
                excludeUrls.add(url[i]);
            }
        }
        logger.info("WebFilter->[{}] init success...", filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException(
                    String.format("%s just supports HTTP requests", this.getClass().getSimpleName()));
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (this.isExcludeUrl(httpRequest, httpResponse)) {
            // 跳过处理
            chain.doFilter(httpRequest, httpResponse);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(httpRequest);
        chain.doFilter(xssRequest, httpResponse);
    }

    /**
     * 当前请求是否是排除的链接
     *
     * @return boolean
     */
    protected boolean isExcludeUrl(HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(request, "HttpServletRequest must not be null");
        Assert.notNull(response, "HttpServletResponse must not be null");
        if (ObjectUtils.isEmpty(excludeUrls)) {
            return false;
        }
        // 返回除去host（域名或者ip）部分的路径
        String requestUri = request.getRequestURI();
        // 返回除去host和工程名部分的路径
        String servletPath = request.getServletPath();
        // 返回全路径
        StringBuffer requestURL = request.getRequestURL();
        for (String pattern : excludeUrls) {
            Matcher m = Pattern.compile("^" + pattern).matcher(requestURL);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }

    /**
     * 需要忽略排除的链接参数名
     */
    public static final String PARAM_NAME_EXCLUSIONS = "exclusions";

    /**
     * 排除链接默认分隔符
     */
    public static final String DEFAULT_SEPARATOR_CHARS = ",";

    /**
     * 需要忽略排除的链接参数值
     * http://localhost,http://127.0.0.1,
     */
    public static final String PARAM_VALUE_EXCLUSIONS = "";

    /**
     * 排除链接
     */
    public List<String> excludeUrls = Lists.newArrayList();

}
