package com.tynet.module.demo.web;

import cn.hutool.core.date.DateTime;
import com.tynet.frame.prj.model.BaseEntity;
import com.tynet.saas.common.hessian.IAppProperties;
import com.tynet.saas.common.service.IResponse;
import com.tynet.saas.common.service.impl.SimpleResponse;
import com.tynet.saas.common.util.DateUtils;
import com.tynet.saas.common.util.Lazy;
import com.tynet.saas.common.util.ReflectionUtils;
import com.tynet.saas.common.util.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 测试控制器
 *
 * @author Created by 思伟 on 2022/1/13
 */
@RestController
@RequestMapping("/test")
public class TestController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IAppProperties appProperties;

    /**
     * 统一请求处理
     *
     * @param servletRequest  ServletRequest
     * @param servletResponse ServletResponse
     * @param json            JSON字符串
     * @return obj
     */
    @RequestMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object request(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
                          @RequestBody(required = false) String json) {
        // 计时
        final long startTime = DateUtils.currentTimeStamp();
        // 从request中获取请求参数
        final Map<String, String> map = servletRequest.getParameterMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> StringUtils.join(entry.getValue(), ",")
                ));

        // 接口响应
        IResponse<?> response;

        try {
            // Assert.hasText(json, "请求入参不能为空...");

            // 结果的提供者（注：不可重复调用消费，建议缓存）
            Supplier<IResponse<?>> responseSupplier = Lazy.of(() -> this.processRequest(json));

            response = responseSupplier.get();
        } catch (Exception e) {
            // 抛出的目标异常
            final Throwable targetException = ReflectionUtils.getRealThrowable(e);
            logger.error("[{}]执行失败 - 系统异常：" + targetException.getMessage(),
                    this.getServiceName(), targetException);
            // 错误响应
            response = SimpleResponse.error(
                    StringUtils.format("系统异常：{}",
                            targetException.getMessage()
                    ), null);
        }
        // response = new SimpleResponseDecorator<>(response);

        // 接口请求耗时
        String durationStr = (DateUtils.currentTimeStamp() - startTime) / 1000d + "s";
        logger.debug("[{}] - 访问：method=[{}],url=[{}],参数=[{}],入参=[{}],耗时[{}],响应数据=[{}]",
                this.getServiceName(),
                servletRequest.getMethod(), servletRequest.getRequestURI(), json, map, durationStr, response);
        /**
         * 系统默认配置为：{@link org.springframework.http.converter.json.MappingJackson2HttpMessageConverter}
         */
        return response;
        // return JSON.toJSONString(response);
    }

    /**
     * 处理请求
     *
     * @param json 请求入参
     * @return 接口响应
     */
    protected @NotNull IResponse<?> processRequest(@NotNull String json) {
        return SimpleResponse.success(new TestEntity());
    }

    /**
     * 获取服务名
     *
     * @return String
     */
    protected @NotNull String getServiceName() {
        return "测试";
    }

    @Data
    @NoArgsConstructor
    public static class TestEntity extends BaseEntity {
        private static final long serialVersionUID = 1L;

        /**
         * 包装实例
         */
        private Long wrapperLong = Long.MAX_VALUE;

        /**
         * 基本类型
         */
        private long primitiveLong = Long.MIN_VALUE;

        /**
         * 当前时间
         */
        private Date date = DateTime.now();

        /**
         * 当前时间
         */
        private LocalDateTime localDateTime = DateUtils.now();

        /**
         * 当前日期
         */
        private LocalDate localDate = DateUtils.nowDate();

        /**
         * 当前时间
         */
        private LocalTime localTime = DateUtils.nowTime();

    }

}
