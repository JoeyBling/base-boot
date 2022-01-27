package com.tynet.saas.common.context.event;

import com.tynet.saas.common.service.ISortAble;

import java.util.EventListener;

/**
 * 自定义排序事件监听器接口
 *
 * @author Created by 思伟 on 2022/1/27
 */
public interface SortAbleEventListener extends EventListener, ISortAble {

}
