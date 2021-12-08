package com.tynet.frame.prjext.handler;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import com.tynet.saas.common.hessian.IStartUp;
import com.tynet.saas.common.service.ISortAble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 回调初始化执行类
 * <p>
 * 观察者设计模式 - for the Observer design pattern.
 * {@link org.springframework.context.event.ContextRefreshedEvent}
 * SpringBoot建议使用：{@link org.springframework.boot.context.event.ApplicationReadyEvent}
 * 如果项目中有多个{@code SpringApplication}或{@code SpringBootServletInitializer}同时运行，则{@code ApplicationReadyEvent}也会运行多次
 * 监听Spring Boot的生命周期事件：
 * 1. {@link org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent} 初始化环境变量
 * 2. {@link org.springframework.boot.context.event.ApplicationPreparedEvent} 初始化完成
 * 3. {@link org.springframework.context.event.ContextRefreshedEvent} 应用刷新
 * 4. {@link ApplicationReadyEvent} 应用已启动完成
 * 5. {@link org.springframework.context.event.ContextStartedEvent} 应用启动，需要在代码动态添加监听器才可捕获
 * 6. {@link org.springframework.context.event.ContextStoppedEvent} 应用停止
 * 7. {@link org.springframework.context.event.ContextClosedEvent} 应用关闭
 * </p>
 *
 * @author Created by 思伟 on 2020/4/23
 */
@Component
public class StartUpHandler implements ApplicationListener<ApplicationReadyEvent>, Ordered {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    // @Autowired
    protected DefaultListableBeanFactory defaultListableBeanFactory;

    /**
     * do stuff now that application is ready
     *
     * @param event 响应的事件
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        final ApplicationContext applicationContext = event.getApplicationContext();
        // root application context 没有parent，他就是老大.
        if (applicationContext.getParent() == null) {
            this.initStartUpBeans(applicationContext);
        } else {
            // 多个响应的事件也需要进行处理...root application context的子容器
            logger.debug("init StartUpBeans by [{}]...", event.getSource());
            this.initStartUpBeans(applicationContext);
        }
    }

    /**
     * 回调初始化执行类
     *
     * @param applicationContext 应用程序上下文
     */
    protected void initStartUpBeans(ApplicationContext applicationContext) {
        Map<String, IStartUp> startUpBeans = applicationContext.getBeansOfType(IStartUp.class);

        if (ObjectUtil.isNotEmpty(startUpBeans)) {
            // 优先级排序后进行回调
            for (IStartUp service : ISortAble.sorted(startUpBeans.values())) {
                try {
                    service.startUp();
                    logger.debug("[{}]初始化回调执行成功...", ClassUtil.getClassName(service, true));
                } catch (RuntimeException e) {
                    logger.error(String.format("启动[%s]失败，e=%s",
                            ClassUtil.getClassName(service, false), e.getMessage()), e);
                } catch (Exception e) {
                    // 对于捕获到非{@code RuntimeException}异常的情况，继续往上抛出...
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public int getOrder() {
        // 返回最低优先级
        return LOWEST_PRECEDENCE;
    }

}
