/*
 * 项目名称:platform-plus
 * 类名称:QkjvipNewsCarouselServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-10 13:46:41        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipNewsCarouselDao;
import com.platform.modules.qkjvip.entity.QkjvipNewsCarouselEntity;
import com.platform.modules.qkjvip.service.QkjvipNewsCarouselService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-05-10 13:46:41
 */
@Service("qkjvipNewsCarouselService")
public class QkjvipNewsCarouselServiceImpl extends ServiceImpl<QkjvipNewsCarouselDao, QkjvipNewsCarouselEntity> implements QkjvipNewsCarouselService {

    @Override
    public List<QkjvipNewsCarouselEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.add_time");
        params.put("asc", false);
        Page<QkjvipNewsCarouselEntity> page = new Query<QkjvipNewsCarouselEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipNewsCarouselPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipNewsCarouselEntity qkjvipNewsCarousel) {
        return this.save(qkjvipNewsCarousel);
    }

    @Override
    public boolean update(QkjvipNewsCarouselEntity qkjvipNewsCarousel) {
        return this.updateById(qkjvipNewsCarousel);
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
