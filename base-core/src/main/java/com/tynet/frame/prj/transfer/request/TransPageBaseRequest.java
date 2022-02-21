package com.tynet.frame.prj.transfer.request;

import lombok.Data;
import lombok.ToString;

/**
 * 分页请求对象基类
 *
 * @author Created by 思伟 on 2022/2/14
 */
@Data
@ToString(callSuper = true)
public class TransPageBaseRequest extends TransBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 是否需要分页
     */
    private Boolean paging;

    /**
     * 每页页数
     */
    private int pageSize = 100;

    /**
     * 第几页
     */
    private int pageNum = 1;

    /**
     * 排序字段
     * <p/> 要求传入格式 columnName[.direction][,columnName[.direction]]
     * <p/> e.g. user_name or user_name.desc or user_name,user_age.desc
     */
    private String sort;

}
