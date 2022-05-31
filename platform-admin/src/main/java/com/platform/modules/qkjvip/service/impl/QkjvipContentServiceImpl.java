/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-23 16:37:04        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipContentDao;
import com.platform.modules.qkjvip.entity.QkjvipContentEntity;
import com.platform.modules.qkjvip.entity.QkjvipContentGroupcontentEntity;
import com.platform.modules.qkjvip.service.QkjvipContentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-03-23 16:37:04
 */
@Service("qkjvipContentService")
public class QkjvipContentServiceImpl extends ServiceImpl<QkjvipContentDao, QkjvipContentEntity> implements QkjvipContentService {

    @Override
    public List<QkjvipContentEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipContentEntity> queryByGroupId(String groupId) {
        return baseMapper.queryByGroupId(groupId);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.add_time");
        params.put("asc", false);
        Page<QkjvipContentEntity> page = new Query<QkjvipContentEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipContentPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipContentEntity qkjvipContent) {
        return this.save(qkjvipContent);
    }

    @Override
    public boolean update(QkjvipContentEntity qkjvipContent) {
        return this.updateById(qkjvipContent);
    }

    @Override
    public boolean updateBatch(List<QkjvipContentEntity> contentList) {
        return this.updateBatchById(contentList, 100);
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
