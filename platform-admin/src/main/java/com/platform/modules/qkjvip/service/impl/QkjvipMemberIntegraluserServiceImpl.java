/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntegraluserServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-21 15:18:24        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberIntegraluserDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntegraluserEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberIntegraluserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2020-12-21 15:18:24
 */
@Service("qkjvipMemberIntegraluserService")
public class QkjvipMemberIntegraluserServiceImpl extends ServiceImpl<QkjvipMemberIntegraluserDao, QkjvipMemberIntegraluserEntity> implements QkjvipMemberIntegraluserService {

    @Override
    public List<QkjvipMemberIntegraluserEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipMemberMessageUserQueryEntity> queryByIntegralId(String integralId) {
        return baseMapper.queryByIntegralId(integralId);
    }

    @Override
    public List<String> queryByIntegralId2(String integralId) {
        return baseMapper.queryByIntegralId2(integralId);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberIntegraluserEntity> page = new Query<QkjvipMemberIntegraluserEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberIntegraluserPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberIntegraluserEntity qkjvipMemberIntegraluser) {
        return this.save(qkjvipMemberIntegraluser);
    }

    @Override
    public void addBatch(List<QkjvipMemberIntegraluserEntity> qkjvipMemberIntegralusers) {
        this.saveBatch(qkjvipMemberIntegralusers, 1000);
    }

    @Override
    public boolean update(QkjvipMemberIntegraluserEntity qkjvipMemberIntegraluser) {
        return this.updateById(qkjvipMemberIntegraluser);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public void deleteByIntegralId(String integralId) {
        baseMapper.deleteByIntegralId(integralId);
    }

    @Override
    public void deleteBatchByIntegralId(String[] integralIds) {
        baseMapper.deleteBatchByIntegralId(integralIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
