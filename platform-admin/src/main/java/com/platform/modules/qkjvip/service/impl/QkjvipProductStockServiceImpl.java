/*
 * 项目名称:platform-plus
 * 类名称:QkjvipProductStockServiceImpl.java
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
import com.platform.modules.qkjvip.dao.QkjvipProductStockDao;
import com.platform.modules.qkjvip.entity.QkjvipProductStockEntity;
import com.platform.modules.qkjvip.service.QkjvipProductStockService;
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
@Service("qkjvipProductStockService")
public class QkjvipProductStockServiceImpl extends ServiceImpl<QkjvipProductStockDao, QkjvipProductStockEntity> implements QkjvipProductStockService {

    @Override
    public List<QkjvipProductStockEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipProductStockEntity> page = new Query<QkjvipProductStockEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipProductStockPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public Page queryPageStock(Map<String, Object> params) {
        //排序
        Page<QkjvipProductStockEntity> page = new Query<QkjvipProductStockEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipProductStockNumPage(page, params));
        page.setTotal(baseMapper.queryAllStockNum(params).size());
        return page;
    }

    @Override
    public List<QkjvipProductStockEntity> queryAllDetail(Map<String, Object> params) {
        return baseMapper.selectQkjvipProductStockNumDetail(params);
    }

    @Override
    public boolean add(QkjvipProductStockEntity qkjvipProductStock) {
        return this.save(qkjvipProductStock);
    }

    @Override
    public boolean update(QkjvipProductStockEntity qkjvipProductStock) {
        return this.updateById(qkjvipProductStock);
    }
    @Override
    public boolean updateShiftOrderStock(QkjvipProductStockEntity qkjvipProductStock) {
        return baseMapper.updateShiftOrderStock(qkjvipProductStock);
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
    public void batchAdd(List<QkjvipProductStockEntity> qkjviporderdetails) {
        this.saveBatch(qkjviporderdetails, 100);
    }
}
