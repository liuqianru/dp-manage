/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberUnitnameServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-17 10:44:55        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberUnitnameDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberUnitnameEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberUnitnameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-11-17 10:44:55
 */
@Service("qkjvipMemberUnitnameService")
public class QkjvipMemberUnitnameServiceImpl extends ServiceImpl<QkjvipMemberUnitnameDao, QkjvipMemberUnitnameEntity> implements QkjvipMemberUnitnameService {

    @Override
    public List<QkjvipMemberUnitnameEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.createtime");
        params.put("asc", false);
        Page<QkjvipMemberUnitnameEntity> page = new Query<QkjvipMemberUnitnameEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberUnitnamePage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberUnitnameEntity qkjvipMemberUnitname) {
        return this.save(qkjvipMemberUnitname);
    }

    @Override
    public boolean update(QkjvipMemberUnitnameEntity qkjvipMemberUnitname) {
        return this.updateById(qkjvipMemberUnitname);
    }

    @Override
    public boolean delete(String unitname) {
        return this.removeById(unitname);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] unitnames) {
        return this.removeByIds(Arrays.asList(unitnames));
    }
}
