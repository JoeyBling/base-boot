package com.tynet;

import cn.hutool.core.map.MapUtil;
import org.apache.catalina.Globals;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;

/**
 * SpringBoot
 *
 * @author Created by 思伟 on 2020/6/6
 */
// 开启`AspectJ`自动代理模式
@EnableAspectJAutoProxy(
        proxyTargetClass = true
        // , exposeProxy = true
)
@EnableScheduling
@ServletComponentScan
/*@tk.mybatis.spring.annotation.MapperScan({
        "com.tynet.**.dao",
        "com.tynet.**.mapper",
})*/
@SpringBootApplication(exclude = {
}, excludeName = {
        // "org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration",
})
public class App extends SpringBootServletInitializer {

    /**
     * Main Application Entry
     *
     * @param args 应用程序参数
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(MapUtil.<String, Object>builder()
                // .put(ConfigDataEnvironmentPostProcessor.ON_LOCATION_NOT_FOUND_PROPERTY, "ignore")
                // .put(ConfigDataEnvironmentPostProcessor.ON_LOCATION_NOT_FOUND_PROPERTY, "IGNORE")
                // .put(ConfigDataEnvironmentPostProcessor.ON_LOCATION_NOT_FOUND_PROPERTY, ConfigDataNotFoundAction.IGNORE)
                // .put(ConfigDataEnvironmentPostProcessor.ON_LOCATION_NOT_FOUND_PROPERTY, ConfigDataNotFoundAction.FAIL)
                // 知乎文章无效...
                // .put("spring.config.on-location-not-found", "IGNORE")
                .build());
        app.setBannerMode(Mode.CONSOLE);

        /**
         * 默认字符集 <p>从JDK18开始，将`UTF-8`指定为标准`Java API`的默认字符集
         */
        final String charsetName = System.getProperty("file.encoding");
        System.out.println("默认字符集：" + charsetName);
        System.out.println("系统默认字符集：" + Charset.defaultCharset().name());

        // 此处需启动后才能获取值
        System.out.println("容器工作目录（路径）：" + System.getProperty(Globals.CATALINA_BASE_PROP));
        System.out.println("容器安装目录（路径）：" + System.getProperty(Globals.CATALINA_HOME_PROP));

        System.out.println(
                String.format("Main Application Entry args = %s", Arrays.toString(args)));
        final ApplicationContext applicationContext = app.run(args);
        System.out.println(
                String.format("Run the Spring application = [%s]", Objects.toString(applicationContext)));
    }

    /**
     * fix : No MyBatis mapper was found in '[xx.mapper]' package. Please check your configuration.
     *
     * @see org.mybatis.spring.mapper.ClassPathMapperScanner#doScan
     * @deprecated tk-mapper与mybatis-starter的冲突
     */
    // @org.apache.ibatis.annotations.Mapper
    @Deprecated
    public interface NoWarnMapper {
    }

}

