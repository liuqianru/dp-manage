/*
 * 项目名称:platform-plus
 * 类名称:QkjvipNewsThumbsupServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-06-04 11:35:48        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipNewsThumbsupDao;
import com.platform.modules.qkjvip.entity.QkjvipNewsThumbsupEntity;
import com.platform.modules.qkjvip.service.QkjvipNewsThumbsupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-06-04 11:35:48
 */
@Service("qkjvipNewsThumbsupService")
public class QkjvipNewsThumbsupServiceImpl extends ServiceImpl<QkjvipNewsThumbsupDao, QkjvipNewsThumbsupEntity> implements QkjvipNewsThumbsupService {

    @Override
    public List<QkjvipNewsThumbsupEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipNewsThumbsupEntity> page = new Query<QkjvipNewsThumbsupEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipNewsThumbsupPage(page, params));
    }

    @Override
    public boolean add(QkjvipNewsThumbsupEntity qkjvipNewsThumbsup) {
        return this.save(qkjvipNewsThumbsup);
    }

    @Override
    public boolean update(QkjvipNewsThumbsupEntity qkjvipNewsThumbsup) {
        return this.updateById(qkjvipNewsThumbsup);
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
    public boolean doDelete(QkjvipNewsThumbsupEntity qkjvipNewsThumbsup) {
        return baseMapper.doDelete(qkjvipNewsThumbsup);
    }
}
