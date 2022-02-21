package com.tynet.frame.prj.transfer.request;

import com.tynet.saas.common.util.DateUtils;
import com.tynet.saas.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础请求对象
 *
 * @author Created by 思伟 on 2022/2/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequest implements IRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 报文时间戳
     */
    private String msgTimestamp = StringUtils.toString(DateUtils.currentTimestamp());

    /**
     * 报文类型
     */
    private String msgType;

    /**
     * 对接编码 - {@literal Docking code}
     */
    private String msgSender;

    /**
     * 报文签名
     */
    private String msgSign;

    /**
     * 报文内容体
     */
    private String msgBody;

}
