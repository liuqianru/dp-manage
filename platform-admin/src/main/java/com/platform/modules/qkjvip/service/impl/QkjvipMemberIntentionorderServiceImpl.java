/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntentionorderServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-01-14 09:44:56        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberIntentionorderDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntentionorderEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberIntentionorderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-01-14 09:44:56
 */
@Service("qkjvipMemberIntentionorderService")
public class QkjvipMemberIntentionorderServiceImpl extends ServiceImpl<QkjvipMemberIntentionorderDao, QkjvipMemberIntentionorderEntity> implements QkjvipMemberIntentionorderService {

    @Override
    public List<QkjvipMemberIntentionorderEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberIntentionorderEntity> page = new Query<QkjvipMemberIntentionorderEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberIntentionorderPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberIntentionorderEntity qkjvipMemberIntentionorder) {
        return this.save(qkjvipMemberIntentionorder);
    }

    @Override
    public void addBatch(List<QkjvipMemberIntentionorderEntity> qkjvipMemberIntentionorderList) {
        this.saveBatch(qkjvipMemberIntentionorderList);
    }

    @Override
    public boolean update(QkjvipMemberIntentionorderEntity qkjvipMemberIntentionorder) {
        return this.updateById(qkjvipMemberIntentionorder);
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

    @Override
    public int deleteByVisitId(String visitId) {
        return baseMapper.removeByVisitId(visitId);
    }

    @Override
    public boolean deleteByVisitIds(String[] visitIds) {
        return baseMapper.removeByVisitIds(visitIds);
    }
}
