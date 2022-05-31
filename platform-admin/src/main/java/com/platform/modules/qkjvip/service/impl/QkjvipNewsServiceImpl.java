/*
 * 项目名称:platform-plus
 * 类名称:QkjvipNewsServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-10 09:37:42        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipNewsDao;
import com.platform.modules.qkjvip.entity.QkjvipNewsEntity;
import com.platform.modules.qkjvip.service.QkjvipNewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-05-10 09:37:42
 */
@Service("qkjvipNewsService")
public class QkjvipNewsServiceImpl extends ServiceImpl<QkjvipNewsDao, QkjvipNewsEntity> implements QkjvipNewsService {

    @Override
    public List<QkjvipNewsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.add_time");
        params.put("asc", false);
        Page<QkjvipNewsEntity> page = new Query<QkjvipNewsEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipNewsPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipNewsEntity qkjvipNews) {
        return this.save(qkjvipNews);
    }

    @Override
    public boolean update(QkjvipNewsEntity qkjvipNews) {
        return this.updateById(qkjvipNews);
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
