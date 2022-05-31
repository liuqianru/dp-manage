/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberCponServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-17 14:46:26        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberCponDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberCponEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberCponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2020-12-17 14:46:26
 */
@Service("qkjvipMemberCponService")
public class QkjvipMemberCponServiceImpl extends ServiceImpl<QkjvipMemberCponDao, QkjvipMemberCponEntity> implements QkjvipMemberCponService {

    @Override
    public List<QkjvipMemberCponEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.add_time");
        params.put("asc", false);
        Page<QkjvipMemberCponEntity> page = new Query<QkjvipMemberCponEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberCponPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public Page queryPageCount(Map<String, Object> params) {
        //排序
        params.put("sidx", "z.id");
        params.put("asc", false);
        Page<QkjvipMemberCponEntity> page = new Query<QkjvipMemberCponEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberCountPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberCponEntity qkjvipMemberCpon) {
        return this.save(qkjvipMemberCpon);
    }

    @Override
    public boolean update(QkjvipMemberCponEntity qkjvipMemberCpon) {
        return this.updateById(qkjvipMemberCpon);
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
