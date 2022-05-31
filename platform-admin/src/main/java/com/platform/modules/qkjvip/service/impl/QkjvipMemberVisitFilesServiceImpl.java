/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitFilesServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-26 15:10:58        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberVisitFilesDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberVisitFilesEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberVisitFilesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-08-26 15:10:58
 */
@Service("qkjvipMemberVisitFilesService")
public class QkjvipMemberVisitFilesServiceImpl extends ServiceImpl<QkjvipMemberVisitFilesDao, QkjvipMemberVisitFilesEntity> implements QkjvipMemberVisitFilesService {

    @Override
    public List<QkjvipMemberVisitFilesEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addTime");
        params.put("asc", false);
        Page<QkjvipMemberVisitFilesEntity> page = new Query<QkjvipMemberVisitFilesEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberVisitFilesPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberVisitFilesEntity qkjvipMemberVisitFiles) {
        return this.save(qkjvipMemberVisitFiles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addBatch(List<QkjvipMemberVisitFilesEntity> list) {
        return this.saveBatch(list);
    }

    @Override
    public boolean update(QkjvipMemberVisitFilesEntity qkjvipMemberVisitFiles) {
        return this.updateById(qkjvipMemberVisitFiles);
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
