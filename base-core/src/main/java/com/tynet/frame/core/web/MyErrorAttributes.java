package com.tynet.frame.core.web;

import com.tynet.saas.common.hessian.IAppProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;

/**
 * 自定义{@link ErrorAttributes}
 *
 * @author Created by 思伟 on 2022/1/4
 */
public class MyErrorAttributes extends DefaultErrorAttributes implements ErrorAttributes {
    /**
     * 程序配置
     */
    protected @NotNull IAppProperties appProperties;

    /**
     * default constructor
     *
     * @param appProperties {@literal impl}.
     */
    public MyErrorAttributes(IAppProperties appProperties) {
        super();
        this.appProperties = Objects.requireNonNull(appProperties, "appProperties must not be null");
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        final Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        if (appProperties.isDebugMode()) {
            errorAttributes.putIfAbsent("author", "試毅-思伟");
        }
        errorAttributes.putIfAbsent("clientId", appProperties.getClientId());
        return errorAttributes;
    }

}
