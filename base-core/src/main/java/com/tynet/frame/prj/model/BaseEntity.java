package com.tynet.frame.prj.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tynet.saas.common.constant.ResponseCodeConst;
import com.tynet.saas.common.exception.SysRuntimeException;
import com.tynet.saas.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 基础实体类
 *
 * @author Created by 思伟 on 2021/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity implements Entity {
    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 是否有效
     */
    // @Enabled
    @Column(name = "enabled")
    @JSONField(serialize = false)
    @JsonIgnore
    private Boolean enabled;

    @Override
    @JSONField(serialize = false)
    @JsonIgnore
    public String getModule() {
        throw new SysRuntimeException(ResponseCodeConst.ERROR_VALIDATE, "谁没有在model中重写getModule方法");
        // return null;
    }

    @Override
    @JSONField(serialize = false)
    @JsonIgnore
    public boolean isNew() {
        return StringUtils.isBlank(id);
    }
}
