package com.tynet.saas.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 基于{@link Supplier}设计的延迟计算(懒加载)工具
 * 不需要显示调用`get`或`toString`方法
 *
 * @author Created by 思伟 on 2020/10/30
 * @link https://blog.csdn.net/qq271859852/article/details/102987430
 * @see Optional#orElseGet
 */
@Slf4j
public class Lazy<T> implements Supplier<T> {

    /**
     * 结果的提供者
     */
    private final Supplier<T> supplier;

    /**
     * 实例化延迟计算类
     *
     * @param supplier Supplier
     * @param <T>      T
     * @return Lazy
     */
    public static <T> Lazy<T> of(Supplier<T> supplier) {
        if (supplier instanceof Lazy) {
            return (Lazy) supplier;
        } else {
            return new Lazy(supplier);
        }
    }

    /**
     * 唯一私有化构造函数
     *
     * @param supplier Supplier
     */
    private Lazy(Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        this.supplier = supplier;
    }

    @Override
    public T get() {
        return supplier.get();
    }

    @Override
    public String toString() {
        // 防止NPE
        return Optional.ofNullable(get()).map(Objects::toString).orElse(null);
    }

    /**
     * just test
     */
    public static void main(String[] args) {
        log.debug("test: {}", of(of(() -> 111)));
        log.debug("test: {}", of(of(() -> null)));
        long beginTime = System.currentTimeMillis();
        log.debug("{}", of(() -> (DateUtils.currentTimestamp() - beginTime) / 1000 + "s"));
        // 如果普通入参，则会进行调用方法。不推荐(如果对象很大或方法执行慢，容易造成性能问题)
        log.trace("invoke remote method, return value: {}", DateUtils.currentSecondTimestamp());
        // 延迟执行，如果日志等级不够，则不会进行计算
        log.trace("invoke remote method, return value: {}", of(DateUtils::currentSecondTimestamp));
        log.trace("invoke remote method, return value: {}", of(DateUtils::currentSecondTimestamp).get());
        log.debug("invoke remote method, return value: {}", of(() -> null));
        log.debug("invoke remote method, return value: {}", of(DateUtils::currentSecondTimestamp));
    }

}
