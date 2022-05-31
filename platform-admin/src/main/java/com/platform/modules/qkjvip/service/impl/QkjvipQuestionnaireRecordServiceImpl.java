/*
 * 项目名称:platform-plus
 * 类名称:QkjvipQuestionnaireRecordServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-21 10:06:03        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipQuestionnaireRecordDao;
import com.platform.modules.qkjvip.entity.QkjvipQuestionnaireRecordEntity;
import com.platform.modules.qkjvip.service.QkjvipQuestionnaireRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-07-21 10:06:03
 */
@Service("qkjvipQuestionnaireRecordService")
public class QkjvipQuestionnaireRecordServiceImpl extends ServiceImpl<QkjvipQuestionnaireRecordDao, QkjvipQuestionnaireRecordEntity> implements QkjvipQuestionnaireRecordService {

    @Override
    public List<QkjvipQuestionnaireRecordEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipQuestionnaireRecordEntity> queryOne(Map<String, Object> params) {
        return baseMapper.queryOne(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addTime");
        params.put("asc", false);
        Page<QkjvipQuestionnaireRecordEntity> page = new Query<QkjvipQuestionnaireRecordEntity>(params).getPage();
        page.setTotal(baseMapper.queryAll(params).size());
        page.setRecords(baseMapper.selectQkjvipQuestionnaireRecordPage(page, params));
        return page;
    }

    @Override
    public boolean add(QkjvipQuestionnaireRecordEntity qkjvipQuestionnaireRecord) {
        return this.save(qkjvipQuestionnaireRecord);
    }

    @Override
    public boolean addBatch(List<QkjvipQuestionnaireRecordEntity> list) {
        return this.saveBatch(list);
    }

    @Override
    public boolean update(QkjvipQuestionnaireRecordEntity qkjvipQuestionnaireRecord) {
        return this.updateById(qkjvipQuestionnaireRecord);
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
