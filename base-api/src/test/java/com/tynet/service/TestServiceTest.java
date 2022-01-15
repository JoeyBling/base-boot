package com.tynet.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tynet.base.BaseAppTest;
import com.tynet.module.demo.service.TestService;
import com.tynet.saas.common.util.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * just test
 *
 * @author Created by 思伟 on 2022/1/12
 */
@Transactional
class TestServiceTest extends BaseAppTest {

    @Autowired
    private TestService testService;

    /**
     * 基础测试
     */
    @Test
    public void baseTest() {
        Assertions.assertNotNull(testService.getCacheTime());
    }

    /**
     * 插入测试
     */
    @Test
    // @Rollback(false)
    public void saveTest() {
        final com.tynet.module.demo.domain.Test entity = new com.tynet.module.demo.domain.Test();
        entity.setId("test");
        entity.setEnabled(true);
        entity.setExtObj(com.tynet.module.demo.domain.Test.ExtraParam.builder()
                .testStr("just_test")
                .build());
        entity.setCreateTime(DateUtils.now());
        Assertions.assertTrue(testService.save(entity));

        // 分页查询
        System.out.println(testService.page(Page.of(1, 10)));

        final com.tynet.module.demo.domain.Test record = testService.getById(entity.getId());
        System.out.println(record);
        System.out.println(JSON.toJSONString(record));
    }

}
