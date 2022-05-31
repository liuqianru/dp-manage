/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberChannelServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-09 10:55:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberChannelDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberChannelEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberChannelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2020-12-09 10:55:27
 */
@Service("qkjvipMemberChannelService")
public class QkjvipMemberChannelServiceImpl extends ServiceImpl<QkjvipMemberChannelDao, QkjvipMemberChannelEntity> implements QkjvipMemberChannelService {

    @Override
    public List<QkjvipMemberChannelEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.MemberChannel");
        params.put("asc", false);
        Page<QkjvipMemberChannelEntity> page = new Query<QkjvipMemberChannelEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberChannelPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberChannelEntity qkjvipMemberChannel) {
        return this.save(qkjvipMemberChannel);
    }

    @Override
    public boolean update(QkjvipMemberChannelEntity qkjvipMemberChannel) {
        return this.updateById(qkjvipMemberChannel);
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
