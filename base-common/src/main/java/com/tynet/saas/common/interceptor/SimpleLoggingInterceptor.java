package com.tynet.saas.common.interceptor;

import com.tynet.saas.common.service.adapter.MyHandlerInterceptorAdapter;
import com.tynet.saas.common.util.HttpUtil;
import com.tynet.saas.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * 统一简单日志拦截器
 *
 * @author Created by 思伟 on 2021/3/29
 */
public class SimpleLoggingInterceptor extends MyHandlerInterceptorAdapter implements AsyncHandlerInterceptor {

    /**
     * 默认本地执行线程开始时间标识
     */
    protected static final ThreadLocal<Long> START_TIME_THREAD_LOCAL = new NamedThreadLocal<Long>("ThreadLocal_StartTime") {
        protected final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        protected Long initialValue() {
            logger.warn("统一日志拦截器错误获取线程绑定变量，使用默认值...");
            return System.currentTimeMillis();
        }
    };
    /**
     * 日期转换格式
     */
    protected final String DATE_PATTERN = "HH:mm:ss.SSS";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 开始时间
        long beginTime = System.currentTimeMillis();
        // 线程绑定变量（该数据只有当前请求的线程可见）
        START_TIME_THREAD_LOCAL.set(beginTime);
        if (logger.isDebugEnabled()) {
            // TODO 考虑如何输出一行
            logger.debug("开始计时: {}, URI: {}, IP: {}, Method: {}", new SimpleDateFormat(DATE_PATTERN).format(beginTime),
                    request.getRequestURI(), HttpUtil.getRemoteAddr(request),
                    request.getMethod());
        }
        return true;
    }

    /**
     * 在业务处理器处理请求之前需要打印的附加日志
     *
     * @return 附加日志信息
     */
    public String preAdditionalLog(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // return StringUtils.format()
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            logger.info("ViewName: " + modelAndView.getViewName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (StringUtils.equalsAny(request.getRequestURI(),
                "/favicon.ico", "", "/")) {
            return;
        }
        try {
            if (logger.isDebugEnabled()) {
                // 得到线程绑定的局部变量（开始时间）
                long beginTime = START_TIME_THREAD_LOCAL.get();
                // 结束时间
                long endTime = System.currentTimeMillis();
                // 打印JVM信息(可选保存日志操作等...)
                logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                        new SimpleDateFormat(DATE_PATTERN).format(endTime), (endTime - beginTime) / 1000d + "s", request.getRequestURI(),
                        Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024,
                        Runtime.getRuntime().freeMemory() / 1024 / 1024,
                        (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
            }
        } finally {
            // 回收自定义的ThreadLocal变量
            START_TIME_THREAD_LOCAL.remove();
        }
    }

    /**
     * 处理异步请求
     *
     * @see org.springframework.scheduling.annotation.AsyncResult
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response,
                                               Object handler) throws Exception {
        logger.debug("After Concurrent Started <==== {}", Thread.currentThread().getId());
    }
}
