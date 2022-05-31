/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportMembertempServiceImpl.java
 * 包名称:com.platform.modules.qkjrpt.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 10:51:12        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjrpt.dao.QkjrptReportMembertempDao;
import com.platform.modules.qkjrpt.entity.*;
import com.platform.modules.qkjrpt.service.QkjrptReportMembertempService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-09-15 10:51:12
 */
@Service("qkjrptReportMembertempService")
public class QkjrptReportMembertempServiceImpl extends ServiceImpl<QkjrptReportMembertempDao, QkjrptReportMembertempEntity> implements QkjrptReportMembertempService {

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.CreateOn");
        params.put("asc", false);
        Page<QkjrptReportMembertempEntity> page = new Query<QkjrptReportMembertempEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrptReportMembertempPage(page, params));
    }

    @Override
    public List<QkjrptReportMembertempEntity> getMemberAreaReport(Map<String, Object> params) {
        return baseMapper.getMemberAreaReport(params);
    }

    @Override
    public List<QkjrptReportMemberAreaLevelEntity> getMemberAreaLevelReport(Map<String, Object> params) {
        return baseMapper.getMemberAreaLevelReport(params);
    }

    @Override
    public List<QkjrptReportMemberAreaLevelEntity> getMemberAreaLevelRatio(Map<String, Object> params) {
        return baseMapper.getMemberAreaLevelRatio(params);
    }

    @Override
    public List<QkjrptReportMemberOrgEntity> getMemberOrgReport(Map<String, Object> params) {
        return baseMapper.getMemberOrgReport(params);
    }

    @Override
    public List<QkjrptReportMemberOrgLevelEntity> getMemberOrgLevelReport(Map<String, Object> params) {
        return baseMapper.getMemberOrgLevelReport(params);
    }

    @Override
    public List<QkjrptReportChartMapEntity> getChartMapReport(Map<String, Object> params) {
        return baseMapper.getChartMapReport(params);
    }
}
