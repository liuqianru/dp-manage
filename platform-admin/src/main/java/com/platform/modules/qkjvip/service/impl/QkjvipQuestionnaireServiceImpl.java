/*
 * 项目名称:platform-plus
 * 类名称:QkjvipQuestionnaireServiceImpl.java
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
import com.platform.modules.qkjvip.dao.QkjvipQuestionnaireDao;
import com.platform.modules.qkjvip.entity.QkjvipQuestionnaireEntity;
import com.platform.modules.qkjvip.entity.QkjvipQuestionnaireExcelEntity;
import com.platform.modules.qkjvip.service.QkjvipQuestionnaireService;
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
@Service("qkjvipQuestionnaireService")
public class QkjvipQuestionnaireServiceImpl extends ServiceImpl<QkjvipQuestionnaireDao, QkjvipQuestionnaireEntity> implements QkjvipQuestionnaireService {

    @Override
    public List<QkjvipQuestionnaireEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addtime");
        params.put("asc", false);
        Page<QkjvipQuestionnaireEntity> page = new Query<QkjvipQuestionnaireEntity>(params).getPage();
        page.setTotal(baseMapper.queryAll(params).size());
        page.setRecords(baseMapper.selectQkjvipQuestionnairePage(page, params));
        return page;
    }

    @Override
    public boolean add(QkjvipQuestionnaireEntity qkjvipQuestionnaire) {
        return this.save(qkjvipQuestionnaire);
    }

    @Override
    public boolean update(QkjvipQuestionnaireEntity qkjvipQuestionnaire) {
        return this.updateById(qkjvipQuestionnaire);
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
    public List<QkjvipQuestionnaireExcelEntity> ExcelList(Map<String, Object> params) {
        List<QkjvipQuestionnaireExcelEntity> list = baseMapper.ExcelList(params);
        return list;
    }

}
