/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportMembertempService.java
 * 包名称:com.platform.modules.qkjrpt.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 10:51:12        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjrpt.entity.*;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2021-09-15 10:51:12
 */
public interface QkjrptReportMembertempService extends IService<QkjrptReportMembertempEntity> {

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return List
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 客户统计-大区
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportMembertempEntity> getMemberAreaReport(Map<String, Object> params);
    /**
     * 客户统计-大区-等级
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportMemberAreaLevelEntity> getMemberAreaLevelReport(Map<String, Object> params);
    /**
     * 客户统计-大区-等级同比环比
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportMemberAreaLevelEntity> getMemberAreaLevelRatio(Map<String, Object> params);
    /**
     * 客户统计-部门
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportMemberOrgEntity> getMemberOrgReport(Map<String, Object> params);
    /**
     * 客户统计-部门-等级
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportMemberOrgLevelEntity> getMemberOrgLevelReport(Map<String, Object> params);
    /**
     * 地图
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportChartMapEntity> getChartMapReport(Map<String, Object> params);
}
