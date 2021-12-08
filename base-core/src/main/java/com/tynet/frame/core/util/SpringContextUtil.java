package com.tynet.frame.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * Spring上下文工具类
 * <p>
 * 多种方式获取{@literal Spring-IOC}容器
 * 1. {@link javax.servlet.ServletContextListener} - {@link org.springframework.web.context.support.WebApplicationContextUtils}
 * 2. {@link org.springframework.context.ApplicationContextAware}
 * 3. {@link org.springframework.context.support.ApplicationObjectSupport}
 * 4. {@link org.springframework.web.context.support.WebApplicationObjectSupport}
 * </p>
 *
 * @author Created by 思伟 on 2021/12/8
 */
@Component
public class SpringContextUtil implements ApplicationContextAware, ServletContextAware {

    /**
     * Spring上下文
     */
    private static ApplicationContext applicationContext = null;

    /**
     * Servlet上下文
     */
    private static ServletContext servletContext;

    /**
     * 获取Spring上下文
     *
     * @return {@code ApplicationContext}
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取Servlet上下文
     *
     * @return {@code ServletContext}
     */
    public static ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    @Override
    public final void setServletContext(ServletContext servletContext) {
        SpringContextUtil.servletContext = servletContext;
    }

    /**
     * 上下文是否必须
     *
     * @return boolean
     */
    protected static boolean isContextRequired() {
        return true;
    }

}
