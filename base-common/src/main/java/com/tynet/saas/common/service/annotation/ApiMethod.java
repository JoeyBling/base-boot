package com.tynet.saas.common.service.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Api接口方法声明
 *
 * @author Created by 思伟 on 2020/8/3
 */
@Target({
        ElementType.METHOD,
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ApiMethod {

    /**
     * Alias for {@link #name}.
     * <p>
     * {@link AliasFor} 注解使用要求：
     * Use: {@link org.springframework.core.annotation.AnnotationUtils}
     * 1. 互为别名的注解必须成对出现
     * 2. 别名属性必须声明相同的返回类型
     * 3. 互为别名的属性必须定义默认值
     * 4. 别名属性必须声明相同的默认值
     * </p>
     */
    @AliasFor(attribute = "name")
    String[] value() default {};

    /**
     * 接口名
     *
     * @return {@code String} array.
     */
    @AliasFor(attribute = "value")
    String[] name() default {};

    /**
     * 接口调用权限(为空不鉴权)
     *
     * @return {@code String} array.
     */
    String[] permission() default {};

    /**
     * TODO 待实现
     * 多个调用权限下权限检查的逻辑操作
     * The logical operation for the permission checks in case multiple user type are specified. OR is the default
     */
    Logical logical() default Logical.OR;

}
