/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentGroupuserServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-31 17:47:01        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipContentGroupuserDao;
import com.platform.modules.qkjvip.entity.QkjvipContentGroupuserEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;
import com.platform.modules.qkjvip.service.QkjvipContentGroupuserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-03-31 17:47:01
 */
@Service("qkjvipContentGroupuserService")
public class QkjvipContentGroupuserServiceImpl extends ServiceImpl<QkjvipContentGroupuserDao, QkjvipContentGroupuserEntity> implements QkjvipContentGroupuserService {

    @Override
    public List<QkjvipContentGroupuserEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipContentGroupuserEntity> page = new Query<QkjvipContentGroupuserEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipContentGroupuserPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipContentGroupuserEntity qkjvipContentGroupuser) {
        return this.save(qkjvipContentGroupuser);
    }

    @Override
    public boolean addBatch(List<QkjvipContentGroupuserEntity> qkjvipContentGroupusers) {
        return this.saveBatch(qkjvipContentGroupusers);
    }

    @Override
    public boolean update(QkjvipContentGroupuserEntity qkjvipContentGroupuser) {
        return this.updateById(qkjvipContentGroupuser);
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
    public boolean deleteByGroupId(String groupId) {
        return baseMapper.deleteByGroupId(groupId);
    }

    @Override
    public List<QkjvipMemberMessageUserQueryEntity> queryByGroupId(String groupId) {
        return baseMapper.queryByGroupId(groupId);
    }
}
