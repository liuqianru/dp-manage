/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberRightsServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-26 16:38:38        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberRightsDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberRightsEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberRightsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-04-26 16:38:38
 */
@Service("qkjvipMemberRightsService")
public class QkjvipMemberRightsServiceImpl extends ServiceImpl<QkjvipMemberRightsDao, QkjvipMemberRightsEntity> implements QkjvipMemberRightsService {

    @Override
    public List<QkjvipMemberRightsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipMemberRightsEntity> queryByGroup(Map<String, Object> params) {
        return baseMapper.queryByGroup(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberRightsEntity> page = new Query<QkjvipMemberRightsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberRightsPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberRightsEntity qkjvipMemberRights) {
        return this.save(qkjvipMemberRights);
    }

    @Override
    public boolean update(QkjvipMemberRightsEntity qkjvipMemberRights) {
        return this.updateById(qkjvipMemberRights);
    }

    @Override
    public boolean updateByMemberlevel(QkjvipMemberRightsEntity qkjvipMemberRights) {
        return baseMapper.updateByMemberlevel(qkjvipMemberRights);
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
