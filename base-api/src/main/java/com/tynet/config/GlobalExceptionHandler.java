package com.tynet.config;

import com.alibaba.fastjson.JSONObject;
import com.tynet.saas.common.constant.ResponseCodeConst;
import com.tynet.saas.common.exception.BaseRuntimeException;
import com.tynet.saas.common.exception.MyException;
import com.tynet.saas.common.hessian.IAppProperties;
import com.tynet.saas.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author Created by 思伟 on 2021/1/6
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected IAppProperties appProperties;

    /**
     * web错误处理的配置属性
     */
    private final ErrorProperties errorProperties;

    /**
     * default constructor
     *
     * @param serverProperties 服务配置
     */
    public GlobalExceptionHandler(ServerProperties serverProperties) {
        // 特殊处理
        this.errorProperties = serverProperties.getError();
    }

    /**
     * 自定义异常处理器
     *
     * @param e       {@link com.tynet.saas.common.exception.MyException}
     * @param request
     * @return obj
     */
    @ResponseBody
    @ExceptionHandler(BaseRuntimeException.class)
    public Object handleMyException(MyException e, HttpServletRequest request) {
        logger.error("系统异常出错：{}", e.toString(), e);
        final JSONObject jobj = new JSONObject();
        jobj.put("code", e.getErrorCode());
        jobj.put("msg", StringUtils.defaultString(e.getMsg(), "系统异常，请联系管理员处理"));
        jobj.put("succ", false);
        return jobj;
    }

    /**
     * 默认异常处理
     *
     * @param exception Throwable
     * @return obj
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public JSONObject defaultErrorHandler(Throwable exception) {
        // 记录异常日志
        logger.error("捕获到未处理的系统异常：{}", exception.getMessage(), exception);
        final JSONObject jobj = new JSONObject();
        jobj.put("code", ResponseCodeConst.ERROR_SYSTEM);
        jobj.put("msg", "系统异常，请联系管理员处理");
        jobj.put("succ", false);
        return jobj;
    }

}
