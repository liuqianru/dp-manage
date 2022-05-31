/*
 * 项目名称:platform-plus
 * 类名称:QkjrptMemberTargetServiceImpl.java
 * 包名称:com.platform.modules.qkjrpt.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-28 13:59:12        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjrpt.dao.QkjrptMemberTargetDao;
import com.platform.modules.qkjrpt.entity.QkjrptMemberTargetEntity;
import com.platform.modules.qkjrpt.service.QkjrptMemberTargetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-09-28 13:59:12
 */
@Service("qkjrptMemberTargetService")
public class QkjrptMemberTargetServiceImpl extends ServiceImpl<QkjrptMemberTargetDao, QkjrptMemberTargetEntity> implements QkjrptMemberTargetService {

    @Override
    public List<QkjrptMemberTargetEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjrptMemberTargetEntity> page = new Query<QkjrptMemberTargetEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrptMemberTargetPage(page, params));
    }

    @Override
    public List<QkjrptMemberTargetEntity> queryReport(Map<String, Object> params) {
        return baseMapper.queryReport(params);
    }

    @Override
    public boolean add(QkjrptMemberTargetEntity qkjrptMemberTarget) {
        return this.save(qkjrptMemberTarget);
    }

    @Override
    public boolean update(QkjrptMemberTargetEntity qkjrptMemberTarget) {
        return this.updateById(qkjrptMemberTarget);
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
