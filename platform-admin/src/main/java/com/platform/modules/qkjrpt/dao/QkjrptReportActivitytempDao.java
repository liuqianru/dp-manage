/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportActivitytempDao.java
 * 包名称:com.platform.modules.qkjrpt.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 13:05:30        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjrpt.entity.QkjrptReportActivitytempEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 孙珊珊
 * @date 2021-09-15 13:05:30
 */
@Mapper
public interface QkjrptReportActivitytempDao extends BaseMapper<QkjrptReportActivitytempEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportActivitytempEntity> queryAll(@Param("params") Map<String, Object> params);

    List<QkjrptReportActivitytempEntity> queryAllRpt(@Param("params") Map<String, Object> params);

    List<QkjrptReportActivitytempEntity> queryAllTypeRpt(@Param("params") Map<String, Object> params);

    List<QkjrptReportActivitytempEntity> queryAllRptTs(@Param("params") Map<String, Object> params);
    List<QkjrptReportActivitytempEntity> queryAllRptSource(@Param("params") Map<String, Object> params);

    List<QkjrptReportActivitytempEntity> queryAllRptTotal(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportActivitytempEntity> selectQkjrptReportActivitytempPage(IPage page, @Param("params") Map<String, Object> params);
}
