package com.tynet.frame.core.annotation;

import java.lang.annotation.*;

/**
 * 是否有效
 *
 * @author Created by 思伟 on 2022/2/21
 */
@Target({
        ElementType.FIELD,
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Enabled {

}
