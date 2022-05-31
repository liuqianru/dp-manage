/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitMaterialServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-02 10:50:33        李鹏军     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberVisitMaterialDao;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberVisitMaterialEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberVisitMaterialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2020-12-02 10:50:33
 */
@Service("qkjvipMemberVisitMaterialService")
public class QkjvipMemberVisitMaterialServiceImpl extends ServiceImpl<QkjvipMemberVisitMaterialDao, QkjvipMemberVisitMaterialEntity> implements QkjvipMemberVisitMaterialService {

    @Override
    public List<QkjvipMemberVisitMaterialEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.add_time");
        params.put("asc", false);
        Page<QkjvipMemberVisitMaterialEntity> page = new Query<QkjvipMemberVisitMaterialEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberVisitMaterialPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public Page queryPageAll(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.add_time");
        params.put("asc", false);
        Page<QkjvipMemberVisitMaterialEntity> page = new Query<QkjvipMemberVisitMaterialEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberVisitMaterialPageAll(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberVisitMaterialEntity qkjvipMemberVisitMaterial) {
        return this.save(qkjvipMemberVisitMaterial);
    }

    @Override
    public void addBatch(List<QkjvipMemberVisitMaterialEntity> visitMaterialList) {
        this.saveBatch(visitMaterialList);
    }

    @Override
    public boolean update(QkjvipMemberVisitMaterialEntity qkjvipMemberVisitMaterial) {
        return this.updateById(qkjvipMemberVisitMaterial);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public int deleteByVisitId(String visitId) {
        return baseMapper.removeByVisitId(visitId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByVisitIds(String[] visitIds) {
        return baseMapper.removeByVisitIds(visitIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
