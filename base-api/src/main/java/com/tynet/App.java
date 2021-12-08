package com.tynet;

import cn.hutool.core.map.MapUtil;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.Objects;

/**
 * SpringBoot
 *
 * @author Created by 思伟 on 2020/6/6
 */
// 开启AspectJ 自动代理模式
//@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
@ServletComponentScan
@SpringBootApplication
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
        System.out.println(
                String.format("Main Application Entry args = %s", Arrays.toString(args)));
        final ApplicationContext applicationContext = app.run(args);
        System.out.println(
                String.format("Run the Spring application = [%s]", Objects.toString(applicationContext)));
    }

}

