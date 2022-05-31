/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberQuickqueryServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-26 14:05:17        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberQuickqueryDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberQuickqueryEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberQuickqueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-07-26 14:05:17
 */
@Service("qkjvipMemberQuickqueryService")
public class QkjvipMemberQuickqueryServiceImpl extends ServiceImpl<QkjvipMemberQuickqueryDao, QkjvipMemberQuickqueryEntity> implements QkjvipMemberQuickqueryService {

    @Override
    public List<QkjvipMemberQuickqueryEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addTime");
        params.put("asc", false);
        Page<QkjvipMemberQuickqueryEntity> page = new Query<QkjvipMemberQuickqueryEntity>(params).getPage();
        page.setTotal(baseMapper.queryAll(params).size());
        page.setRecords(baseMapper.selectQkjvipMemberQuickqueryPage(page, params));
        return page.setRecords(baseMapper.selectQkjvipMemberQuickqueryPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberQuickqueryEntity qkjvipMemberQuickquery) {
        return this.save(qkjvipMemberQuickquery);
    }

    @Override
    public boolean update(QkjvipMemberQuickqueryEntity qkjvipMemberQuickquery) {
        return this.updateById(qkjvipMemberQuickquery);
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
