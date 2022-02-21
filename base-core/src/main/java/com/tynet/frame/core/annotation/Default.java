package com.tynet.frame.core.annotation;

import java.lang.annotation.*;

/**
 * 默认值
 *
 * @author Created by 思伟 on 2022/2/21
 */
@Target({
        ElementType.FIELD,
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Default {

    /**
     * 值
     *
     * @return {@code String}.
     */
    String value() default "";

}
