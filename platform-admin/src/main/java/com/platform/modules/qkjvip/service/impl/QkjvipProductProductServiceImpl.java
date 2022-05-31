/*
 * 项目名称:platform-plus
 * 类名称:QkjvipProductProductServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-10 11:37:08        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipProductProductDao;
import com.platform.modules.qkjvip.entity.QkjvipProductProductEntity;
import com.platform.modules.qkjvip.service.QkjvipProductProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-03-10 11:37:08
 */
@Service("qkjvipProductProductService")
public class QkjvipProductProductServiceImpl extends ServiceImpl<QkjvipProductProductDao, QkjvipProductProductEntity> implements QkjvipProductProductService {

    @Override
    public List<QkjvipProductProductEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipProductProductEntity> page = new Query<QkjvipProductProductEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipProductProductPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipProductProductEntity qkjvipProductProduct) {
        return this.save(qkjvipProductProduct);
    }

    @Override
    public boolean update(QkjvipProductProductEntity qkjvipProductProduct) {
        return this.updateById(qkjvipProductProduct);
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
