/*
 * 项目名称:platform-plus
 * 类名称:QkjcusOrderServiceImpl.java
 * 包名称:com.platform.modules.qkjcus.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-23 16:50:14             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjcus.dao.QkjcusOrderDao;
import com.platform.modules.qkjcus.entity.QkjcusOrderEntity;
import com.platform.modules.qkjcus.service.QkjcusOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 
 * @date 2021-12-23 16:50:14
 */
@Service("qkjcusOrderService")
public class QkjcusOrderServiceImpl extends ServiceImpl<QkjcusOrderDao, QkjcusOrderEntity> implements QkjcusOrderService {

    @Override
    public List<QkjcusOrderEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.add_time");
        params.put("asc", false);
        Page<QkjcusOrderEntity> page = new Query<QkjcusOrderEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjcusOrderPage(page, params));
    }

    @Override
    public boolean add(QkjcusOrderEntity qkjcusOrder) {
        return this.save(qkjcusOrder);
    }

    @Override
    public boolean update(QkjcusOrderEntity qkjcusOrder) {
        return this.updateById(qkjcusOrder);
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
