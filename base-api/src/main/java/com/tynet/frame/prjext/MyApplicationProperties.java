package com.tynet.frame.prjext;

import com.tynet.frame.prj.ApplicationProperties;
import com.tynet.saas.common.hessian.IStartUp;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自定义程序配置属性 - 扩展配置
 *
 * @author Created by 思伟 on 2021/12/3
 */
@Data
@Validated
@Primary
@Component
@ConfigurationProperties(prefix = MyApplicationProperties.MY_PROPERTIES_PREFIX)
@ConfigurationPropertiesBinding
public class MyApplicationProperties implements IStartUp {
    /**
     * 自定义程序配置属性前缀
     */
    public static final String MY_PROPERTIES_PREFIX = ApplicationProperties.APPLICATION_CONFIG_PREFIX + ".prj";

    /**
     * 序列号生成的workId - 多机负载时需要修改
     */
    @NotNull
    private long sequenceWorkId;

    /**
     * 仅仅测试
     */
    @Nullable
    @Deprecated
    private String testStr;

    @Deprecated
    @DeprecatedConfigurationProperty(reason = "暂时弃用")
    public String getTestStr() {
        return testStr;
    }

    @Override
    public void startUp() throws Exception {
        // call itself
        this.init();
    }

    /**
     * 执行初始化操作
     */
    public void init() {
        // 原子性操作
        if (!initFlag.compareAndSet(false, true)) {
            return;
        }
    }

    /**
     * 是否已初始化完成
     *
     * @return boolean
     */
    public final boolean isInitialized() {
        return Boolean.TRUE.equals(initFlag.get());
    }

    /**
     * 初始化标识
     */
    @Getter(AccessLevel.NONE)
    protected final AtomicBoolean initFlag = new AtomicBoolean(false);

}
