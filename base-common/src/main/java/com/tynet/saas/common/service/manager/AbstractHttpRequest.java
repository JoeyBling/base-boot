package com.tynet.saas.common.service.manager;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.tynet.saas.common.constant.ResponseCodeConst;
import com.tynet.saas.common.constant.SystemConst;
import com.tynet.saas.common.exception.SysRuntimeException;
import com.tynet.saas.common.service.IResponse;
import com.tynet.saas.common.service.decorator.PrefixSlfLoggerDecorator;
import com.tynet.saas.common.service.impl.SingletonInitializingBean;
import com.tynet.saas.common.util.DateUtils;
import com.tynet.saas.common.util.StringUtils;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * 公用简单API接口Http请求抽象实现
 * <p>
 * </p>
 *
 * @param <T> 响应数据对象
 * @author Created by 思伟 on 2021/4/23
 */
public abstract class AbstractHttpRequest<T> extends SingletonInitializingBean {

    /**
     * one public constructor
     *
     * @param apiDomain API接口域名
     */
    public AbstractHttpRequest(String apiDomain) {
        this.apiDomain = Objects.requireNonNull(apiDomain);
    }

    /**
     * 获取日志记录器
     * <p>Defaults {@link #INNER_LOGGER}
     *
     * @return {@code org.slf4j.Logger}
     */
    protected final Logger getLogger() {
        if (null == INNER_LOGGER) {
            synchronized (this) {
                // 双检锁/双重校验锁
                if (null == INNER_LOGGER) {
                    INNER_LOGGER = LoggerFactory.getLogger(this.getClass());
                    if (null != INNER_LOGGER) {
                        INNER_LOGGER = decorateLogger(INNER_LOGGER);
                    }
                }
            }
        }
        return INNER_LOGGER;
    }

    /**
     * 装饰给定的日志对象
     * <p>
     * 子类如果要重写，建议主动声明再次调用父类一次，否则日志前缀会忽略打印
     * </p>
     *
     * @param logger 日志对象
     * @return 装饰后的对象
     */
    protected Logger decorateLogger(Logger logger) {
        return new PrefixSlfLoggerDecorator(logger, this.getLogPrefix());
    }

    /**
     * 获取服务名
     *
     * @return String
     */
    protected abstract @NotNull String getServiceName();

    /**
     * 校验接口是否请求成功，否则抛出异常
     *
     * @param result 接口返回信息
     * @return {@link T}
     */
    protected abstract @NotNull IResponse<T> checkApiResult(final String result);

    /**
     * {@link #sendRequest(String, Method, Object, Map, String, String)}
     */
    protected @NotNull IResponse<T> sendRequest(final String reqUrl, final Method method,
                                                final Object bodyObj, Map<String, String> headers) {
        return this.sendRequest(reqUrl, method, bodyObj, headers, null, null);
    }

    /**
     * 统一请求API接口
     *
     * @param reqUrl      请求的URL
     * @param method      请求方法(默认POST) {@link Method#POST}
     * @param bodyObj     请求参数
     * @param headers     请求头
     * @param contentType 内容编码类型
     * @param acceptType  接收编码类型
     * @return IResponse
     */
    protected @NotNull IResponse<T> sendRequest(final String reqUrl, final Method method,
                                                final Object bodyObj, Map<String, String> headers,
                                                final String contentType, final String acceptType) {
        // 默认POST请求
        final HttpRequest httpRequest = HttpUtil.createRequest(
                Optional.ofNullable(method).orElse(Method.POST), reqUrl)
                .contentType(Optional.ofNullable(contentType).orElse(this.getDefaultContentType()))
                .header(Header.ACCEPT, Optional.ofNullable(acceptType).orElse(this.getDefaultAcceptType()))
                .headerMap(headers, true)
                .setConnectionTimeout(this.getDefaultConnectionTimeout())
                .setReadTimeout(this.getDefaultReadTimeout());
        // 请求参数
        if (ObjectUtil.isNotEmpty(bodyObj)) {
            // 入参适配不同的请求方法
            switch (httpRequest.getMethod()) {
                case GET:
                    final String urlGetParams = com.tynet.saas.common.util.HttpUtil.
                            generateGetParams(httpRequest.getUrl(), bodyObj);
                    httpRequest.setUrl(urlGetParams);
                    break;
                default:
                    // 有可能带上了编码信息 `application/x-www-form-urlencoded;charset=UTF-8`
                    if (StringUtils.contains(httpRequest.header(Header.CONTENT_TYPE), ContentType.FORM_URLENCODED.toString())) {
                        httpRequest.form((Map<String, Object>) bodyObj);
                    } else {
                        // 默认JSON请求
                        httpRequest.body(JSON.toJSONString(bodyObj));
                    }
            }
        }

        getLogger().debug("请求API接口：method=[{}],url=[{}],参数=[{}]",
                httpRequest.getMethod(), httpRequest.getUrl(), bodyObj);
        // 计时
        long startTime = DateUtils.currentTimeStamp();
        // 执行Request请求
        try (HttpResponse response = httpRequest.execute()) {
            // 接口请求耗时
            String durationStr = (DateUtils.currentTimeStamp() - startTime) / 1000d + "s";
            // 响应主体
            String result = null;
            if (response.isOk()) {
                result = response.body();
                getLogger().debug("请求API接口成功,耗时[{}],响应数据=[{}]", durationStr, result);
            } else {
                getLogger().warn("请求API接口失败,耗时[{}],状态码=[{}],响应主体=[{}]",
                        durationStr, response.getStatus(), response.body());
            }
            return this.checkApiResult(result);
        } catch (Throwable e) {
            /**
             * {@link cn.hutool.core.io.IORuntimeException} 连接超时
             * {@link HttpException}
             */
            throw new SysRuntimeException(ResponseCodeConst.ERROR_SYSTEM,
                    StringUtils.format("[{}]请求API接口失败,e={}", this.getServiceName(), e.getMessage()));
        }
    }

    /**
     * 获取默认连接超时(单位：毫秒)
     *
     * @return int
     */
    protected int getDefaultConnectionTimeout() {
        return Math.toIntExact(Duration.ofSeconds(3).toMillis());
    }

    /**
     * 获取默认读取超时(单位：毫秒)
     *
     * @return int
     */
    protected int getDefaultReadTimeout() {
        return Math.toIntExact(Duration.ofSeconds(3).toMillis());
    }

    /**
     * 获取全局默认内容编码类型
     * <p>
     * {@link org.springframework.http.MediaType}
     * </p>
     *
     * @return {@link ContentType}
     */
    protected String getDefaultContentType() {
        return ContentType.JSON.toString(SystemConst.DEFAULT_CHARSET);
    }

    /**
     * 获取全局默认接收编码类型
     *
     * @return {@link ContentType}
     */
    protected String getDefaultAcceptType() {
        return ContentType.JSON.toString(SystemConst.DEFAULT_CHARSET);
    }

    @Override
    protected void singletonInit() {
        // 初始化操作
        getLogger().info("Http请求实现初始化完成...");
    }

    /**
     * 获取日志前缀
     *
     * @return String
     */
    private String getLogPrefix() {
        return StringUtils.format("[{}]", this.getServiceName());
    }

    /**
     * 将响应转为JSON对象
     * <p>
     * 提供的默认响应对象处理
     * </p>
     *
     * @param result 接口响应数据
     * @return JSONObject
     */
    protected <T extends CharSequence> JSONObject getJsonObj(final T result) {
        /**
         * 获取JSON对象函数
         * <p>
         * 适应返回任意JSON形式
         * 1. {@link JSONObject} JSON对象 {@link JSON#parseObject}
         * 2. {@link JSONArray} JSON数组 {@link JSON#parseArray}
         * </p>
         */
        final Function<T, JSONObject> GET_JSON_OBJ_FUNCTION = t -> {
            final Object jsonResult = Optional.ofNullable(JSON.parse(t.toString())).orElse(new JSONObject());
            if (null == jsonResult) {
                // NPE
            } else if (JSONObject.class.isAssignableFrom(jsonResult.getClass())) {
                return (JSONObject) jsonResult;
            } else if (JSONArray.class.isAssignableFrom(jsonResult.getClass())) {
                // 如果是JSON数组，取第一个对象 (注意下标溢出)
                return (JSONObject) ((JSONArray) jsonResult).get(0);
            } else {
                // TODO 特殊对象是否开放子类重写
                getLogger().warn("未处理的响应对象[{}]", jsonResult.getClass().getName());
            }
            throw new JSONException("接口响应异常");
        };
        return GET_JSON_OBJ_FUNCTION.apply(result);
    }

    /**
     * 默认日志记录器
     */
    private volatile Logger INNER_LOGGER = null;
    /**
     * API接口域名
     */
    @Getter
    private String apiDomain;

}
