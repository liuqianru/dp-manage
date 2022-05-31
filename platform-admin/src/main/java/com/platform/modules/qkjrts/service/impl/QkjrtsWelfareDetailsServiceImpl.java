/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareDetailsServiceImpl.java
 * 包名称:com.platform.modules.qkjrts.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjrts.dao.QkjrtsWelfareDetailsDao;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareDetailsEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
@Service("qkjrtsWelfareDetailsService")
public class QkjrtsWelfareDetailsServiceImpl extends ServiceImpl<QkjrtsWelfareDetailsDao, QkjrtsWelfareDetailsEntity> implements QkjrtsWelfareDetailsService {

    @Override
    public List<QkjrtsWelfareDetailsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjrtsWelfareDetailsEntity> page = new Query<QkjrtsWelfareDetailsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrtsWelfareDetailsPage(page, params));
    }

    @Override
    public boolean add(QkjrtsWelfareDetailsEntity qkjrtsWelfareDetails) {
        return this.save(qkjrtsWelfareDetails);
    }

    @Override
    public void addBatch(List<QkjrtsWelfareDetailsEntity> welfarelist) {
        this.saveBatch(welfarelist, 1000);
    }

    @Override
    public boolean update(QkjrtsWelfareDetailsEntity qkjrtsWelfareDetails) {
        return this.updateById(qkjrtsWelfareDetails);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public void deleteByMainId(String id) {
        baseMapper.deleteByMainId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
