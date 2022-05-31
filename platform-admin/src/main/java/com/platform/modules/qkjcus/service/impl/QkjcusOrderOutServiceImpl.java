/*
 * 项目名称:platform-plus
 * 类名称:QkjcusOrderOutServiceImpl.java
 * 包名称:com.platform.modules.qkjcus.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-28 10:14:18             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjcus.dao.QkjcusOrderOutDao;
import com.platform.modules.qkjcus.entity.QkjcusOrderOutEntity;
import com.platform.modules.qkjcus.service.QkjcusOrderOutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 
 * @date 2021-12-28 10:14:18
 */
@Service("qkjcusOrderOutService")
public class QkjcusOrderOutServiceImpl extends ServiceImpl<QkjcusOrderOutDao, QkjcusOrderOutEntity> implements QkjcusOrderOutService {

    @Override
    public List<QkjcusOrderOutEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjcusOrderOutEntity> page = new Query<QkjcusOrderOutEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjcusOrderOutPage(page, params));
    }

    @Override
    public boolean add(QkjcusOrderOutEntity qkjcusOrderOut) {
        return this.save(qkjcusOrderOut);
    }

    @Override
    public boolean update(QkjcusOrderOutEntity qkjcusOrderOut) {
        return this.updateById(qkjcusOrderOut);
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
