package com.tynet.frame.prj.transfer.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 请求对象基类
 *
 * @author Created by 思伟 on 2022/2/14
 */
@Data
public class TransBaseRequest implements IRequest, Serializable {
    private static final long serialVersionUID = 1L;

    /** 应用ID(服务商ID) */
    private String appid;
    /** 服务商渠道 */
    private String channel;
    /** 服务商渠道版本 */
    private String version;
    /** 接口名称 */
    private String service;
    /** 校验码 */
    private String sign;
    /** 随机码 */
    private String random;
    /** 返回数据格式(默认JSON) */
    private String format;
    /** IP地址 */
    private String ip;
    /** 操作时间 */
    private Date operateTime = new Date();
    /**
     * 客户端参数 - 响应中返回
     */
    private Map<String, Object> clientParams;

    /**
     * 检索键
     */
    private String searchKey;

    /**
     * 检索条件集合
     */
    private Map<String, Object> searchMap;

}
