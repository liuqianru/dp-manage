/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentGroupcontentServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-24 15:41:50        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipContentGroupcontentDao;
import com.platform.modules.qkjvip.entity.QkjvipContentEntity;
import com.platform.modules.qkjvip.entity.QkjvipContentGroupcontentEntity;
import com.platform.modules.qkjvip.service.QkjvipContentGroupcontentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-03-24 15:41:50
 */
@Service("qkjvipContentGroupcontentService")
public class QkjvipContentGroupcontentServiceImpl extends ServiceImpl<QkjvipContentGroupcontentDao, QkjvipContentGroupcontentEntity> implements QkjvipContentGroupcontentService {

    @Override
    public List<QkjvipContentGroupcontentEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.sortvalue");
        params.put("asc", true);
        Page<QkjvipContentGroupcontentEntity> page = new Query<QkjvipContentGroupcontentEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipContentGroupcontentPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipContentGroupcontentEntity qkjvipContentGroupcontent) {
        return this.save(qkjvipContentGroupcontent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(List<QkjvipContentGroupcontentEntity> cgcList) {
        this.saveBatch(cgcList);
    }

    @Override
    public boolean update(QkjvipContentGroupcontentEntity qkjvipContentGroupcontent) {
        return this.updateById(qkjvipContentGroupcontent);
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
}
