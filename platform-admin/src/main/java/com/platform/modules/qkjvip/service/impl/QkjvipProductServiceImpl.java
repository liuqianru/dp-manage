/*
 * 项目名称:platform-plus
 * 类名称:QkjvipProductServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-23 15:23:32        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipProductDao;
import com.platform.modules.qkjvip.entity.QkjvipProductEntity;
import com.platform.modules.qkjvip.service.QkjvipProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-08-23 15:23:32
 */
@Service("qkjvipProductService")
public class QkjvipProductServiceImpl extends ServiceImpl<QkjvipProductDao, QkjvipProductEntity> implements QkjvipProductService {

    @Override
    public List<QkjvipProductEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.CreateTime");
        params.put("asc", false);
        Page<QkjvipProductEntity> page = new Query<QkjvipProductEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipProductPage(page, params));
    }

    @Override
    public boolean add(QkjvipProductEntity qkjvipProduct) {
        return this.save(qkjvipProduct);
    }

    @Override
    public boolean update(QkjvipProductEntity qkjvipProduct) {
        return this.updateById(qkjvipProduct);
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
