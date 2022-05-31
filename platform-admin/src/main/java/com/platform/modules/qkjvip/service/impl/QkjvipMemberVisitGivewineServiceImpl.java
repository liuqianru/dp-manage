/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitGivewineServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-24 09:16:08        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberVisitGivewineDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberVisitGivewineEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberVisitGivewineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-08-24 09:16:08
 */
@Service("qkjvipMemberVisitGivewineService")
public class QkjvipMemberVisitGivewineServiceImpl extends ServiceImpl<QkjvipMemberVisitGivewineDao, QkjvipMemberVisitGivewineEntity> implements QkjvipMemberVisitGivewineService {

    @Override
    public List<QkjvipMemberVisitGivewineEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addTime");
        params.put("asc", false);
        Page<QkjvipMemberVisitGivewineEntity> page = new Query<QkjvipMemberVisitGivewineEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberVisitGivewinePage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberVisitGivewineEntity qkjvipMemberVisitGivewine) {
        return this.save(qkjvipMemberVisitGivewine);
    }

    @Override
    public boolean update(QkjvipMemberVisitGivewineEntity qkjvipMemberVisitGivewine) {
        return this.updateById(qkjvipMemberVisitGivewine);
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
