package com.tynet.saas.common.service;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义优先级排序接口
 * <p>
 * 值最小的对象具有最高的优先级
 * 应用场景：
 * 1. 多个通知列表需要进行排序后进行优先级通知
 * </p>
 *
 * @author Created by 思伟 on 2021/2/2
 */
public interface ISortAble extends Comparable<ISortAble> {

    /**
     * 获取优先级顺序值
     * <p>
     * 最高的优先级 {@link org.springframework.core.Ordered#HIGHEST_PRECEDENCE}
     * 最低优先级 {@link org.springframework.core.Ordered#LOWEST_PRECEDENCE}
     * </p>
     *
     * @return int
     */
    default int getSort() {
        return 0;
    }

    /**
     * 自定义排序
     * <p>
     * 非必要建议不可重写此方法
     * </p>
     *
     * @param o 排序对象
     * @return int
     */
    @Override
    default int compareTo(ISortAble o) {
        if (null == o) {
            return -1;
        }
        if (this.getSort() < o.getSort()) {
            // 小的往前排
            return -1;
        }
        if (this.getSort() > o.getSort()) {
            // 大的往后排
            return 1;
        }
        return 0;
    }

    /**
     * 对列表按优先级进行排序
     * <p>
     * 使用默认排序规则
     * </p>
     *
     * @param ctx 需要进行排序的列表
     * @return {@link #sorted(Collection, boolean)} )}
     */
    static <E extends ISortAble, T extends Collection<E>> List<E> sorted(@NotNull T ctx) {
        return sorted(ctx, false);
    }

    /**
     * 对列表按优先级进行排序
     *
     * @param ctx      需要进行排序的列表
     * @param reversed 是否逆序(反转排序)
     * @param <E>      实现此接口的类
     * @param <T>      列表
     * @return 排序后的列表
     */
    static <E extends ISortAble, T extends Collection<E>> List<E> sorted(@NotNull T ctx, boolean reversed) {
        // NPE check
        Objects.requireNonNull(ctx);
        // 比较器
        final Comparator<E> comparator = ISortAble::compareTo;
        final Comparator<E> ableComparator = reversed ? Collections.reverseOrder(comparator) : comparator;
        // final Comparator<E> ableComparator = reversed ? comparator.reversed() : comparator;
        return ctx.stream().sorted(ableComparator).collect(Collectors.toList());
    }

}
