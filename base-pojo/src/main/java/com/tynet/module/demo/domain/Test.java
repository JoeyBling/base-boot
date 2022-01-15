package com.tynet.module.demo.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tynet.frame.prj.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 测试
 *
 * @author Created by 思伟 on 2022/1/12
 */
@Data
@NoArgsConstructor
@Table(name = "test")
public class Test extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Override
    @JSONField(serialize = false)
    public String getModule() {
        return null;
    }

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 扩展参数(JSON) {@link ExtraParam}
     * <p>
     * read-only
     * </p>
     */
    @Column(name = "extra_params")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JSONField(serialize = false)
    private String extraParams;

    /**
     * 获取扩展属性
     *
     * @return pojo
     */
    public @NotNull ExtraParam getExtObj() {
        return Optional.ofNullable(
                JSON.parseObject(this.getExtraParams(), ExtraParam.class)
        ).orElseGet(() -> ExtraParam.builder().build());
    }

    /**
     * 设置扩展属性
     *
     * @param ext pojo
     */
    public void setExtObj(ExtraParam ext) {
        this.extraParams = JSON.toJSONString(ext);
    }

    /**
     * 扩展属性
     *
     * @author Created by 思伟 on 2021/6/17
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ExtraParam
            // implements Serializable
    {

        /**
         * 防止构造器报错 - 后续删除
         */
        private String testStr;

    }

}
