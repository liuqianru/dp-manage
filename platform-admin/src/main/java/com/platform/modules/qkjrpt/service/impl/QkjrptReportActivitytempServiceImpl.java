/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportActivitytempServiceImpl.java
 * 包名称:com.platform.modules.qkjrpt.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 13:05:30        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjrpt.dao.QkjrptReportActivitytempDao;
import com.platform.modules.qkjrpt.entity.QkjrptReportActivitytempEntity;
import com.platform.modules.qkjrpt.service.QkjrptReportActivitytempService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-09-15 13:05:30
 */
@Service("qkjrptReportActivitytempService")
public class QkjrptReportActivitytempServiceImpl extends ServiceImpl<QkjrptReportActivitytempDao, QkjrptReportActivitytempEntity> implements QkjrptReportActivitytempService {

    @Override
    public List<QkjrptReportActivitytempEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjrptReportActivitytempEntity> queryAllRpt(Map<String, Object> params) {
        return baseMapper.queryAllRpt(params);
    }

    @Override
    public List<QkjrptReportActivitytempEntity> queryAllTypeRpt(Map<String, Object> params) {
        return baseMapper.queryAllTypeRpt(params);
    }

    @Override
    public List<QkjrptReportActivitytempEntity> queryAllRptSource(Map<String, Object> params) {
        return baseMapper.queryAllRptSource(params);
    }

    @Override
    public List<QkjrptReportActivitytempEntity> queryAllRptTs(Map<String, Object> params) {
        return baseMapper.queryAllRptTs(params);
    }

    @Override
    public List<QkjrptReportActivitytempEntity> queryAllRptTotal(Map<String, Object> params) {
        return baseMapper.queryAllRptTotal(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjrptReportActivitytempEntity> page = new Query<QkjrptReportActivitytempEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrptReportActivitytempPage(page, params));
    }

    @Override
    public boolean add(QkjrptReportActivitytempEntity qkjrptReportActivitytemp) {
        return this.save(qkjrptReportActivitytemp);
    }

    @Override
    public boolean update(QkjrptReportActivitytempEntity qkjrptReportActivitytemp) {
        return this.updateById(qkjrptReportActivitytemp);
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
