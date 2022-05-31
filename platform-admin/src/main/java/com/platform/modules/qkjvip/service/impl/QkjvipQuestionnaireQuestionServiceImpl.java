/*
 * 项目名称:platform-plus
 * 类名称:QkjvipQuestionnaireQuestionServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-16 10:27:08        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipQuestionnaireQuestionDao;
import com.platform.modules.qkjvip.entity.QkjvipQuestionnaireQuestionEntity;
import com.platform.modules.qkjvip.service.QkjvipQuestionnaireQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-07-16 10:27:08
 */
@Service("qkjvipQuestionnaireQuestionService")
public class QkjvipQuestionnaireQuestionServiceImpl extends ServiceImpl<QkjvipQuestionnaireQuestionDao, QkjvipQuestionnaireQuestionEntity> implements QkjvipQuestionnaireQuestionService {

    @Override
    public List<QkjvipQuestionnaireQuestionEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addtime");
        params.put("asc", true);
        Page<QkjvipQuestionnaireQuestionEntity> page = new Query<QkjvipQuestionnaireQuestionEntity>(params).getPage();
        page.setTotal(baseMapper.queryAll(params).size());
        page.setRecords(baseMapper.selectQkjvipQuestionnaireQuestionPage(page, params));
        return page;
    }

    @Override
    public boolean add(QkjvipQuestionnaireQuestionEntity qkjvipQuestionnaireQuestion) {
        return this.save(qkjvipQuestionnaireQuestion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addBatch(List<QkjvipQuestionnaireQuestionEntity> questionList) {
        return this.saveBatch(questionList);
    }

    @Override
    public boolean update(QkjvipQuestionnaireQuestionEntity qkjvipQuestionnaireQuestion) {
        return this.updateById(qkjvipQuestionnaireQuestion);
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

    @Override
    public boolean deleteByMainIds(String[] ids) {
        return baseMapper.deleteByMainIds(Arrays.asList(ids));
    }
}
