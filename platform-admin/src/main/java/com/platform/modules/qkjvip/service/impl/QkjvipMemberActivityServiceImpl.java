/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivityServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-03 09:49:29        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberActivityDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivityAccountEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivityEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2020-09-03 09:49:29
 */
@Service("qkjvipMemberActivityService")
public class QkjvipMemberActivityServiceImpl extends ServiceImpl<QkjvipMemberActivityDao, QkjvipMemberActivityEntity> implements QkjvipMemberActivityService {

    @Override
    public List<QkjvipMemberActivityEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipMemberActivityEntity> queryAllReport(Map<String, Object> params) {
        return baseMapper.queryAllReport(params);
    }

    @Override
    public List<QkjvipMemberActivityEntity> actityisexist() {
        return baseMapper.actityisexist();
    }

    @Override
    public List<QkjvipMemberActivityAccountEntity> queryCount(Map<String, Object> params) {
        return baseMapper.queryCount(params);
    }

    @Override
    public Page queryAllSignAddress(Map<String, Object> params) {
        params.put("sidx", "act.end_date");
        params.put("asc", false);
        Page<QkjvipMemberActivityEntity> page = new Query<QkjvipMemberActivityEntity>(params).getPage();
        page.setRecords(baseMapper.queryAllSignAddress(page,params));
        page.setTotal(baseMapper.queryAllSignAddress(params).size());
        return page;
    }

    @Override
    public Page queryAllSignAddressmain(Map<String, Object> params) {
        Page<QkjvipMemberActivityEntity> page = new Query<QkjvipMemberActivityEntity>(params).getPage();
        page.setRecords(baseMapper.queryAllSignAddressmain(page,params));
        page.setTotal(baseMapper.queryAllSignAddressmain(params).size());
        return page;
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addtime");
        params.put("asc", false);
        Page<QkjvipMemberActivityEntity> page = new Query<QkjvipMemberActivityEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberActivityPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public Page queryPageCount(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addtime");
        params.put("asc", false);
        Page<QkjvipMemberActivityEntity> page = new Query<QkjvipMemberActivityEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberActivityPageCount(page, params));
    }

    @Override
    public boolean add(QkjvipMemberActivityEntity qkjvipMemberActivity) {
        return this.save(qkjvipMemberActivity);
    }

    @Override
    public boolean update(QkjvipMemberActivityEntity qkjvipMemberActivity) {
        return this.updateById(qkjvipMemberActivity);
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
