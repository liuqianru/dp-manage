/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportGroupdistributeDao.java
 * 包名称:com.platform.modules.qkjrpt.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-11 15:11:18        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjrpt.domain.QkjrptGroupDistributeStatic;
import com.platform.modules.qkjrpt.entity.QkjrptReportGroupdistributeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author hanjie
 * @date 2021-11-11 15:11:18
 */
@Mapper
public interface QkjrptReportGroupdistributeDao extends BaseMapper<QkjrptReportGroupdistributeEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportGroupdistributeEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportGroupdistributeEntity> selectQkjrptReportGroupdistributePage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 查询经销商区域统计数据
     *
     * @param params
     * @return
     */
    List<QkjrptGroupDistributeStatic> getPrimaryAreaDst(@Param("params") Map<String,Object> params);

    List<QkjrptGroupDistributeStatic> getSecondaryAreaDst(@Param("params") Map<String,Object> params);

    List<QkjrptGroupDistributeStatic> getTertiaryAreaDst(@Param("params") Map<String,Object> params);
}
