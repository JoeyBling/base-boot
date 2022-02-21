package com.tynet.frame.prjext.transfer.request;

import com.tynet.frame.prj.transfer.request.TransPageBaseRequest;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * 自定义请求对象
 *
 * @author Created by 思伟 on 2022/2/21
 */
@Data
@ToString(callSuper = true)
public class MyTransPageBaseRequest extends TransPageBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 扩展参数
     */
    private Map<String, Object> extraParams;

}
