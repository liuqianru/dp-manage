/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderOrderfileServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-10 11:37:09        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipOrderOrderfileDao;
import com.platform.modules.qkjvip.entity.QkjvipOrderOrderfileEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderOrderfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-03-10 11:37:09
 */
@Service("qkjvipOrderOrderfileService")
public class QkjvipOrderOrderfileServiceImpl extends ServiceImpl<QkjvipOrderOrderfileDao, QkjvipOrderOrderfileEntity> implements QkjvipOrderOrderfileService {

    @Override
    public List<QkjvipOrderOrderfileEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipOrderOrderfileEntity> page = new Query<QkjvipOrderOrderfileEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipOrderOrderfilePage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipOrderOrderfileEntity qkjvipOrderOrderfile) {
        return this.save(qkjvipOrderOrderfile);
    }

    @Override
    public boolean update(QkjvipOrderOrderfileEntity qkjvipOrderOrderfile) {
        return this.updateById(qkjvipOrderOrderfile);
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
