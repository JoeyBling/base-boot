package com.tynet.saas.common.service.decorator;

import com.tynet.saas.common.util.StringUtils;
import org.slf4j.Logger;

/**
 * 基于{@link org.slf4j.Logger}日志前缀装饰器实现
 *
 * @author Created by 思伟 on 2021/4/23
 */
public class PrefixSlfLoggerDecorator extends AbstractSlfLoggerDecorator {
    /**
     * 日志前缀
     */
    private final String logPrefix;

    /**
     * one public constructor
     *
     * @param target    要装饰的目标对象
     * @param logPrefix 日志前缀 - 需自行添加分隔符
     */
    public PrefixSlfLoggerDecorator(Logger target, String logPrefix) {
        super(target);
        this.logPrefix = logPrefix;
    }

    @Override
    public void debug(String msg) {
        super.debug(addLogPrefix(msg));
    }

    @Override
    public void debug(String format, Object arg) {
        super.debug(addLogPrefix(format), arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        super.debug(addLogPrefix(format), arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        super.debug(addLogPrefix(format), arguments);
    }

    @Override
    public void info(String msg) {
        super.info(addLogPrefix(msg));
    }

    @Override
    public void info(String format, Object arg) {
        super.info(addLogPrefix(format), arg);
    }

    @Override
    public void info(String format, Object... arguments) {
        super.info(addLogPrefix(format), arguments);
    }

    @Override
    public void warn(String msg) {
        super.warn(addLogPrefix(msg));
    }

    @Override
    public void warn(String format, Object arg) {
        super.warn(addLogPrefix(format), arg);
    }

    @Override
    public void warn(String format, Object... arguments) {
        super.warn(addLogPrefix(format), arguments);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        super.warn(addLogPrefix(format), arg1, arg2);
    }

    @Override
    public void error(String msg) {
        super.error(addLogPrefix(msg));
    }

    @Override
    public void error(String format, Object arg) {
        super.error(addLogPrefix(format), arg);
    }

    @Override
    public void error(String format, Object... arguments) {
        super.error(addLogPrefix(format), arguments);
    }

    /**
     * 添加日志前缀
     * <p>
     * 会影响性能吗？
     * </p>
     *
     * @param format the format string
     * @return 格式化后的字符串
     */
    protected String addLogPrefix(String format) {
        return StringUtils.format("{}{}", this.logPrefix, format);
    }

}
