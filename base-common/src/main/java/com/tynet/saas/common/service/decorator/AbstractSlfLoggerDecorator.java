package com.tynet.saas.common.service.decorator;

import com.tynet.saas.common.service.IDecoratorAble;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * 基于{@link Logger}抽象装饰器实现
 * <p>
 * 简化了真实装饰器类的写法，默认调用实现
 * 子类只重写他们感兴趣的方法 - 对标于适配器
 * </p>
 *
 * @author Created by 思伟 on 2021/4/23
 */
public abstract class AbstractSlfLoggerDecorator extends AbstractDecorator<Logger>
        implements IDecoratorAble<Logger>, Logger {

    /**
     * one public constructor
     *
     * @param target 要装饰的目标对象
     */
    public AbstractSlfLoggerDecorator(Logger target) {
        super(target);
    }

    @Override
    public String getName() {
        return this.target.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return this.target.isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        this.target.trace(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        this.target.trace(format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        this.target.trace(format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        this.target.trace(format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        this.target.trace(msg, t);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return this.target.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String msg) {
        this.target.trace(marker, msg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        this.target.trace(marker, format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        this.target.trace(marker, format, arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        this.target.trace(marker, format, argArray);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        this.target.trace(marker, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.target.isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        this.target.debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        this.target.debug(format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        this.target.debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        this.target.debug(format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        this.target.debug(msg, t);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return this.target.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String msg) {
        this.target.debug(marker, msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        this.target.debug(marker, format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        this.target.debug(marker, format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        this.target.debug(marker, format, arguments);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        this.target.debug(marker, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return this.target.isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        this.target.info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        this.target.info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        this.target.info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        this.target.info(format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        this.target.info(msg, t);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return this.target.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String msg) {
        this.target.info(marker, msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        this.target.info(marker, format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        this.target.info(marker, format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        this.target.info(marker, format, arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        this.target.info(marker, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return this.target.isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        this.target.warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        this.target.warn(format, arg);
    }

    @Override
    public void warn(String format, Object... arguments) {
        this.target.warn(format, arguments);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        this.target.warn(format, arg1, arg2);
    }

    @Override
    public void warn(String msg, Throwable t) {
        this.target.warn(msg, t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return this.target.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String msg) {
        this.target.warn(marker, msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        this.target.warn(marker, format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        this.target.warn(marker, format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        this.target.warn(marker, format, arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        this.target.warn(marker, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return this.target.isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        this.target.error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        this.target.error(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        this.target.error(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        this.target.error(format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        this.target.error(msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return this.target.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String msg) {
        this.target.error(marker, msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        this.target.error(marker, format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        this.target.error(marker, format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        this.target.error(marker, format, arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        this.target.error(marker, msg, t);
    }

}
