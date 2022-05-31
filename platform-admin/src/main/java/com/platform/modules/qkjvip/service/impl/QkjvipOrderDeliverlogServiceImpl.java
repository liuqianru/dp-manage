/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderDeliverlogServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-15 15:49:03        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipOrderDeliverlogDao;
import com.platform.modules.qkjvip.entity.QkjvipOrderDeliverlogEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderDeliverlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-03-15 15:49:03
 */
@Service("qkjvipOrderDeliverlogService")
public class QkjvipOrderDeliverlogServiceImpl extends ServiceImpl<QkjvipOrderDeliverlogDao, QkjvipOrderDeliverlogEntity> implements QkjvipOrderDeliverlogService {

    @Override
    public List<QkjvipOrderDeliverlogEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.createOn");
        params.put("asc", false);
        Page<QkjvipOrderDeliverlogEntity> page = new Query<QkjvipOrderDeliverlogEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipOrderDeliverlogPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipOrderDeliverlogEntity qkjvipOrderDeliverlog) {
        return this.save(qkjvipOrderDeliverlog);
    }

    @Override
    public boolean update(QkjvipOrderDeliverlogEntity qkjvipOrderDeliverlog) {
        return this.updateById(qkjvipOrderDeliverlog);
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
    public int deleteBatchByOrder(String morderid) {
        return baseMapper.deleteBatchByOrder(morderid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<QkjvipOrderDeliverlogEntity> qkjviporderdetails) {
        this.saveBatch(qkjviporderdetails, 100);
    }
}
