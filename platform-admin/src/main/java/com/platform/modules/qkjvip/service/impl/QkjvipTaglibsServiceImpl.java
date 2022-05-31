/*
 * 项目名称:platform-plus
 * 类名称:QkjvipTaglibsServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-26 14:20:07        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipTaglibsDao;
import com.platform.modules.qkjvip.entity.QkjvipTaglibsEntity;
import com.platform.modules.qkjvip.service.QkjvipTaglibsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2020-08-26 14:20:07
 */
@Service("qkjvipTaglibsService")
public class QkjvipTaglibsServiceImpl extends ServiceImpl<QkjvipTaglibsDao, QkjvipTaglibsEntity> implements QkjvipTaglibsService {

    @Override
    public List<QkjvipTaglibsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addTime");
        params.put("asc", false);
        Page<QkjvipTaglibsEntity> page = new Query<QkjvipTaglibsEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipTaglibsPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public List<QkjvipTaglibsEntity> queryToCheck(Map<String, Object> params) {
        return baseMapper.queryToCheck(params);
    }

    @Override
    public boolean add(QkjvipTaglibsEntity qkjvipTaglibs) {
        return this.save(qkjvipTaglibs);
    }

    @Override
    public boolean update(QkjvipTaglibsEntity qkjvipTaglibs) {
        return this.updateById(qkjvipTaglibs);
    }

    @Override
    public boolean delete(String labelId) {
        return this.removeById(labelId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] labelIds) {
        return this.removeByIds(Arrays.asList(labelIds));
    }
}
