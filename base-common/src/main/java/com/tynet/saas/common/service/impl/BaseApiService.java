package com.tynet.saas.common.service.impl;

import com.tynet.saas.common.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Api接口抽象类实现
 *
 * @author Created by 思伟 on 2020/9/10
 */
public abstract class BaseApiService implements ApiService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

}
