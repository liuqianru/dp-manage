/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberCponsonServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-17 14:46:26        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberCponsonDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberCponsonEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberCponsonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2020-12-17 14:46:26
 */
@Service("qkjvipMemberCponsonService")
public class QkjvipMemberCponsonServiceImpl extends ServiceImpl<QkjvipMemberCponsonDao, QkjvipMemberCponsonEntity> implements QkjvipMemberCponsonService {

    @Override
    public List<QkjvipMemberCponsonEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberCponsonEntity> page = new Query<QkjvipMemberCponsonEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberCponsonPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberCponsonEntity qkjvipMemberCponson) {
        return this.save(qkjvipMemberCponson);
    }

    @Override
    public boolean update(QkjvipMemberCponsonEntity qkjvipMemberCponson) {
        return this.updateById(qkjvipMemberCponson);
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
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<QkjvipMemberCponsonEntity> qkjvipMemberCponsons) {
        this.saveBatch(qkjvipMemberCponsons, 100);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(List<QkjvipMemberCponsonEntity> qkjvipMemberCponsons) {
        this.updateBatchById(qkjvipMemberCponsons, 100);
    }

    @Override
    public int deleteBatchByOrder(String mainid) {
        return baseMapper.deleteBatchByOrder(mainid);
    }
    @Override
    public List<QkjvipMemberMessageUserQueryEntity> queryByCponId(String cponId) {
        return baseMapper.queryByCponId(cponId);
    }


}
