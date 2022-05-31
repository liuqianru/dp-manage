/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitGivedetailServiceImpl.java
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
import com.platform.modules.qkjvip.dao.QkjvipMemberVisitGivedetailDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberVisitGivedetailEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberVisitGivedetailService;
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
@Service("qkjvipMemberVisitGivedetailService")
public class QkjvipMemberVisitGivedetailServiceImpl extends ServiceImpl<QkjvipMemberVisitGivedetailDao, QkjvipMemberVisitGivedetailEntity> implements QkjvipMemberVisitGivedetailService {

    @Override
    public List<QkjvipMemberVisitGivedetailEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addTime");
        params.put("asc", true);
        Page<QkjvipMemberVisitGivedetailEntity> page = new Query<QkjvipMemberVisitGivedetailEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberVisitGivedetailPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public List<QkjvipMemberMessageUserQueryEntity> queryByMainId(String mainId) {
        return baseMapper.queryByMainId(mainId);
    }

    @Override
    public boolean add(QkjvipMemberVisitGivedetailEntity qkjvipMemberVisitGivedetail) {
        return this.save(qkjvipMemberVisitGivedetail);
    }

    @Override
    public boolean addBatch(List<QkjvipMemberVisitGivedetailEntity> list) {
        return this.saveBatch(list);
    }

    @Override
    public boolean update(QkjvipMemberVisitGivedetailEntity qkjvipMemberVisitGivedetail) {
        return this.updateById(qkjvipMemberVisitGivedetail);
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

    @Override
    public boolean deleteByMainId(String mainId) {
        return baseMapper.deleteByMainId(mainId);
    }
}
