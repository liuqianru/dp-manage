/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportMembertempDao.java
 * 包名称:com.platform.modules.qkjrpt.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 10:51:12        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.modules.qkjrpt.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2021-09-15 10:51:12
 */
@Mapper
public interface QkjrptReportMembertempDao extends BaseMapper<QkjrptReportMembertempEntity> {

    List<QkjrptReportMembertempEntity> getMemberAreaReport(@Param("params") Map<String, Object> params);

    List<QkjrptReportMemberAreaLevelEntity> getMemberAreaLevelReport(@Param("params") Map<String, Object> params);

    List<QkjrptReportChartMapEntity> getChartMapReport(@Param("params") Map<String, Object> params);

    List<QkjrptReportMemberOrgEntity> getMemberOrgReport(@Param("params") Map<String, Object> params);

    List<QkjrptReportMemberOrgLevelEntity> getMemberOrgLevelReport(@Param("params") Map<String, Object> params);

    List<QkjrptReportMemberAreaLevelEntity> getMemberAreaLevelRatio(@Param("params") Map<String, Object> params);

    List<QkjrptReportMembertempEntity> selectQkjrptReportMembertempPage(IPage page, @Param("params") Map<String, Object> params);
}
