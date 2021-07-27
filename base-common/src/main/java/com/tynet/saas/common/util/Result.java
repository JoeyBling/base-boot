package com.tynet.saas.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * 延迟结果对象包装类
 * <p>
 * 防止缓存穿透等问题，使空对象也可进行缓存
 * </p>
 *
 * @author Created by 思伟 on 2020/12/18
 */
@Slf4j
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 目标对象
     */
    private final T obj;

    /**
     * 返回空对象包装类
     *
     * @return Result
     */
    public static <E> Result<E> empty() {
        return of(null);
    }

    /**
     * 实例化结果对象包装类
     *
     * @param obj 目标对象
     * @param <T> T
     * @return Result
     */
    public static <T> Result<T> of(T obj) {
        if (obj instanceof Result) {
            return (Result<T>) obj;
        }
        return new Result<T>(obj);
    }

    /**
     * 唯一私有化构造函数
     *
     * @param obj 目标对象
     */
    private Result(T obj) {
        this.obj = obj;
    }

    /**
     * 获取目标对象
     *
     * @return T
     */
    public T get() {
        return obj;
    }

    @Override
    public String toString() {
        // 防止NPE
        return Optional.ofNullable(get()).map(Objects::toString).orElse(null);
        // 防止NPE
        // return Objects.toString(get());
    }

    /**
     * just test
     */
    public static void main(String[] args) {
        // 模拟缓存空对象
        log.debug("test: {}", empty());
        log.debug("test: {}", new Result<>(null));
        log.debug("test: {}", of(null));
        log.debug("test: {}", of(of(null)));
        log.debug("test not null: {}", of(of(111)).get());
    }

}
