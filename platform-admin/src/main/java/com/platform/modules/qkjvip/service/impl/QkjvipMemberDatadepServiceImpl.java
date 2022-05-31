/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberDatadepServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 17:33:07        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberDatadepDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberDatadepEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberDatadepService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-08-04 17:33:07
 */
@Service("qkjvipMemberDatadepService")
public class QkjvipMemberDatadepServiceImpl extends ServiceImpl<QkjvipMemberDatadepDao, QkjvipMemberDatadepEntity> implements QkjvipMemberDatadepService {

    @Override
    public List<QkjvipMemberDatadepEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberDatadepEntity> page = new Query<QkjvipMemberDatadepEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberDatadepPage(page, params));
    }

    @Override
    public QkjvipMemberDatadepEntity queryByOrgNo(String orgNo) {
        return baseMapper.selectOne(new QueryWrapper<QkjvipMemberDatadepEntity>().eq("OrgNo", orgNo).eq("Disabled", 0));
    }

    @Override
    public boolean add(QkjvipMemberDatadepEntity qkjvipMemberDatadep) {
        return this.save(qkjvipMemberDatadep);
    }

    @Override
    public boolean update(QkjvipMemberDatadepEntity qkjvipMemberDatadep) {
        return this.updateById(qkjvipMemberDatadep);
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
