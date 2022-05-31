/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrderServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-11 14:15:23        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberOrderDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrderEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2020-08-11 14:15:23
 */
@Service("qkjvipMemberOrderService")
public class QkjvipMemberOrderServiceImpl extends ServiceImpl<QkjvipMemberOrderDao, QkjvipMemberOrderEntity> implements QkjvipMemberOrderService {

    @Override
    public List<QkjvipMemberOrderEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipMemberOrderEntity> queryOrderIdList(List<QkjvipMemberOrderEntity> mbList) {
        return baseMapper.queryOrderIdList(mbList);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "convert(datetime,T.orderdate, 20)");
        params.put("asc", false);
        Page<QkjvipMemberOrderEntity> page = new Query<QkjvipMemberOrderEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberOrderPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberOrderEntity qkjvipMemberOrder) {
        return this.save(qkjvipMemberOrder);
    }

    @Override
    public void addBatch(List<QkjvipMemberOrderEntity> memberOrderList) {
        this.saveBatch(memberOrderList);
    }

    @Override
    public boolean update(QkjvipMemberOrderEntity qkjvipMemberOrder) {
        return this.updateById(qkjvipMemberOrder);
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
    public void updateBatch(List<QkjvipMemberOrderEntity> mbList) {
        baseMapper.updateBatchByOrderyId(mbList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<QkjvipMemberOrderEntity> mbList) {
        baseMapper.deleteBatchByOrderyId(mbList);
    }

    @Override
    public int deleteBatchByOrder(List<QkjvipMemberOrderEntity> mbList) {
        return baseMapper.deleteBatchByOrder(mbList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(List<QkjvipMemberOrderEntity> mbList) {
        this.saveBatch(mbList, 1000);
    }
}
