/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntegralruleServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-01-12 10:05:39        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberIntegralruleDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntegralruleEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberIntegralruleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-01-12 10:05:39
 */
@Service("qkjvipMemberIntegralruleService")
public class QkjvipMemberIntegralruleServiceImpl extends ServiceImpl<QkjvipMemberIntegralruleDao, QkjvipMemberIntegralruleEntity> implements QkjvipMemberIntegralruleService {

    @Override
    public List<QkjvipMemberIntegralruleEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberIntegralruleEntity> page = new Query<QkjvipMemberIntegralruleEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberIntegralrulePage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberIntegralruleEntity qkjvipMemberIntegralrule) {
        return this.save(qkjvipMemberIntegralrule);
    }

    @Override
    public boolean update(QkjvipMemberIntegralruleEntity qkjvipMemberIntegralrule) {
        return this.updateById(qkjvipMemberIntegralrule);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
