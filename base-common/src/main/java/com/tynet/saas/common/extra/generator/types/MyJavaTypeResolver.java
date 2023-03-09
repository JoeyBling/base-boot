package com.tynet.saas.common.extra.generator.types;

import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;
import java.util.Properties;

/**
 * 自定义类型解析器 <p>需作为插件依赖运行
 *
 * @author Created by 思伟 on 2022/11/30
 */
public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl implements JavaTypeResolver {

    /**
     * ---------- 备注（扩展） ----------
     * <p>参考：
     * 1. MySQL Types：https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-type-conversions.html
     * 2. 配置参考：https://mybatis.org/generator/configreference/javaTypeResolver.html
     */

    /**
     * default constructor
     */
    public MyJavaTypeResolver() {
        super();

        /**
         * 扩展处理
         */
        super.typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT",
                new FullyQualifiedJavaType(Integer.class.getName())));
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
    }

}
