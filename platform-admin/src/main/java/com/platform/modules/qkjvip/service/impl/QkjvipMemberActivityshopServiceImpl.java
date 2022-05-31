/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivityshopServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-22 16:21:27             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberActivityshopDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivityshopEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActivityshopService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 
 * @date 2022-04-22 16:21:27
 */
@Service("qkjvipMemberActivityshopService")
public class QkjvipMemberActivityshopServiceImpl extends ServiceImpl<QkjvipMemberActivityshopDao, QkjvipMemberActivityshopEntity> implements QkjvipMemberActivityshopService {

    @Override
    public List<QkjvipMemberActivityshopEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberActivityshopEntity> page = new Query<QkjvipMemberActivityshopEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberActivityshopPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberActivityshopEntity qkjvipMemberActivityshop) {
        return this.save(qkjvipMemberActivityshop);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<QkjvipMemberActivityshopEntity> shops) {
        this.saveBatch(shops, 100);
    }

    @Override
    public boolean update(QkjvipMemberActivityshopEntity qkjvipMemberActivityshop) {
        return this.updateById(qkjvipMemberActivityshop);
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
    public int deleteBatchByMainId(String activityId) {
        return baseMapper.deleteBatchByMainId(activityId);
    }
}
