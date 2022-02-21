package com.tynet.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tynet.saas.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@literal Jackson}配置
 *
 * @author Created by 思伟 on 2022/1/14
 */
@Configuration
public class JacksonConfig {

    /**
     * 配置属性
     */
    @Autowired
    private JacksonProperties jacksonProperties;

    /**
     * 日期时间格式
     *
     * @deprecated 不推荐使用此写法
     */
    @Deprecated
    @Value("${spring.jackson.date-format:#{T(com.tynet.saas.common.util.DateUtils).DATE_TIME_PATTERN}}")
    private String datePattern;

    /**
     * 自定义模块 - 仅自定义声明
     */
    // @Bean
    @Deprecated
    public Module customizeModule() {
        // 自定义序列化方式
        SimpleModule simpleModule = new SimpleModule();
        // 避免大整数导致前端精度丢失的问题 - 基本类型&包装实例
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);

        // Java 8日期
        simpleModule.addSerializer(
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_PATTERN)));
        simpleModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_PATTERN)));
        return simpleModule;
    }

    /**
     * 自定义配置回调接口 - 最低优先级
     * <p>
     * {@literal Number.MAX_SAFE_INTEGER} - JavaScript最大的安全整数
     * </p>
     */
    @Order(Ordered.LOWEST_PRECEDENCE)
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer objectMapperBuilderCustomizer() {
        return builder -> {
            builder
                    // 避免大整数导致前端精度丢失的问题 - 基本类型&包装实例
                    .serializerByType(Long.TYPE, ToStringSerializer.instance)
                    .serializerByType(Long.class, ToStringSerializer.instance)
                    /**
                     * Java 8日期 {@link com.fasterxml.jackson.datatype.jsr310.JavaTimeModule}
                     */
                    .serializers(new LocalDateTimeSerializer(DateUtils.NORM_DATE_TIME_FORMATTER))
                    .deserializers(new LocalDateTimeDeserializer(DateUtils.NORM_DATE_TIME_FORMATTER))

            // .serializers(new LocalDateSerializer(DateUtils.NORM_DATE_FORMATTER))
            // .deserializers(new LocalDateDeserializer(DateUtils.NORM_DATE_FORMATTER))

            // .serializers(new LocalTimeSerializer(DateUtils.NORM_TIME_FORMATTER))
            // .deserializers(new LocalTimeDeserializer(DateUtils.NORM_TIME_FORMATTER))
            ;
        };
    }

}
