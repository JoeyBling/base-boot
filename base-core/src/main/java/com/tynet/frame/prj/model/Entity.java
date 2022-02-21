package com.tynet.frame.prj.model;

import java.io.Serializable;

/**
 * 基础实体类接口 - 建议所有类都实现此接口类
 *
 * @author Created by 思伟 on 2021/10/20
 */
public interface Entity extends Serializable {

    /**
     * 获取所属模块
     *
     * @return String
     */
    String getModule();

    /**
     * 是否为新数据
     *
     * @return boolean
     */
    boolean isNew();

}
