/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentSharerecordsServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-01 17:01:46        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipContentSharerecordsDao;
import com.platform.modules.qkjvip.entity.QkjvipContentSharerecordsEntity;
import com.platform.modules.qkjvip.service.QkjvipContentSharerecordsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-04-01 17:01:46
 */
@Service("qkjvipContentSharerecordsService")
public class QkjvipContentSharerecordsServiceImpl extends ServiceImpl<QkjvipContentSharerecordsDao, QkjvipContentSharerecordsEntity> implements QkjvipContentSharerecordsService {

    @Override
    public List<QkjvipContentSharerecordsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipContentSharerecordsEntity> page = new Query<QkjvipContentSharerecordsEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipContentSharerecordsPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipContentSharerecordsEntity qkjvipContentSharerecords) {
        return this.save(qkjvipContentSharerecords);
    }

    @Override
    public boolean update(QkjvipContentSharerecordsEntity qkjvipContentSharerecords) {
        return this.updateById(qkjvipContentSharerecords);
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
