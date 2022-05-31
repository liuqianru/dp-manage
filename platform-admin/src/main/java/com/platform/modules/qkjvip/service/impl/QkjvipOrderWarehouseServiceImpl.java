/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderWarehouseServiceImpl.java
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
import com.platform.modules.qkjvip.dao.QkjvipOrderWarehouseDao;
import com.platform.modules.qkjvip.entity.QkjvipOrderWarehouseEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderWarehouseService;
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
@Service("qkjvipOrderWarehouseService")
public class QkjvipOrderWarehouseServiceImpl extends ServiceImpl<QkjvipOrderWarehouseDao, QkjvipOrderWarehouseEntity> implements QkjvipOrderWarehouseService {

    @Override
    public List<QkjvipOrderWarehouseEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipOrderWarehouseEntity> queryAllStock(Map<String, Object> params) {
        return baseMapper.queryAllStock(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipOrderWarehouseEntity> page = new Query<QkjvipOrderWarehouseEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipOrderWarehousePage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public Page queryMagPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "bh.Hindex,a.Hindex");
        params.put("asc", true);
        Page<QkjvipOrderWarehouseEntity> page = new Query<QkjvipOrderWarehouseEntity>(params).getPage();
        page.setRecords(baseMapper.selectMagWarehousePage(page, params));
        page.setTotal(baseMapper.queryAllMag(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipOrderWarehouseEntity qkjvipOrderWarehouse) {
        return this.save(qkjvipOrderWarehouse);
    }

    @Override
    public boolean update(QkjvipOrderWarehouseEntity qkjvipOrderWarehouse) {
        return this.updateById(qkjvipOrderWarehouse);
    }

    @Override
    public int updateRoots() {
        return baseMapper.updateRoots();
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
    public List<QkjvipOrderWarehouseEntity> queryList(Map<String, Object> params) {
        return baseMapper.queryList(params);
    }
    @Override
    public List<QkjvipOrderWarehouseEntity> queryStatisticsList(Map<String, Object> params) {
        return baseMapper.queryStatisticsList(params);
    }
    @Override
    public List<QkjvipOrderWarehouseEntity> querySelectList(Map<String, Object> params) {
        return baseMapper.querySelectList(params);
    }
}
