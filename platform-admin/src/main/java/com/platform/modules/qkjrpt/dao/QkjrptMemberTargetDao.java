/*
 * 项目名称:platform-plus
 * 类名称:QkjrptMemberTargetDao.java
 * 包名称:com.platform.modules.qkjrpt.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-28 13:59:12        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjrpt.entity.QkjrptMemberTargetEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2021-09-28 13:59:12
 */
@Mapper
public interface QkjrptMemberTargetDao extends BaseMapper<QkjrptMemberTargetEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptMemberTargetEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjrptMemberTargetEntity> selectQkjrptMemberTargetPage(IPage page, @Param("params") Map<String, Object> params);

    List<QkjrptMemberTargetEntity> queryReport(@Param("params") Map<String, Object> params);
}
