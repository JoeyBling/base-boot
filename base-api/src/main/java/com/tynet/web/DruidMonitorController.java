package com.tynet.web;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.tynet.saas.common.hessian.IAppProperties;
import com.tynet.saas.common.service.impl.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@literal Druid}监控相关
 *
 * @author Created by 思伟 on 2021/12/24
 */
@RestController
@RequestMapping(value = "/monitor/druid")
public class DruidMonitorController {

    @Autowired
    protected IAppProperties appProperties;

    /**
     * 统一请求处理
     *
     * @param servletRequest  ServletRequest
     * @param servletResponse ServletResponse
     * @param json            JSON字符串
     * @return obj
     */
    @RequestMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object request(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
                          @RequestBody(required = false) String json) {
        return SimpleResponse.success(null);
    }

    /**
     * 获取监控数据
     *
     * @return obj
     */
    @GetMapping("/stat")
    public Object druidStat() {
        // 获取所有数据源的监控数据
        return SimpleResponse.success(
                DruidStatManagerFacade.getInstance().getDataSourceStatDataList()
        );
    }

}
