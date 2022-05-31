/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderErporderServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-06-21 09:21:21        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipOrderErporderDao;
import com.platform.modules.qkjvip.entity.QkjvipOrderErporderEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderErporderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-06-21 09:21:21
 */
@Service("qkjvipOrderErporderService")
public class QkjvipOrderErporderServiceImpl extends ServiceImpl<QkjvipOrderErporderDao, QkjvipOrderErporderEntity> implements QkjvipOrderErporderService {

    @Override
    public List<QkjvipOrderErporderEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipOrderErporderEntity> queryAllDetail(Map<String, Object> params) {
        return baseMapper.queryAllDetail(params);
    }


    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.orderdate");
        params.put("asc", false);
        Page<QkjvipOrderErporderEntity> page = new Query<QkjvipOrderErporderEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipOrderErporderPage(page, params));
    }

    @Override
    public boolean add(QkjvipOrderErporderEntity qkjvipOrderErporder) {
        return this.save(qkjvipOrderErporder);
    }

    @Override
    public boolean update(QkjvipOrderErporderEntity qkjvipOrderErporder) {
        return this.updateById(qkjvipOrderErporder);
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
