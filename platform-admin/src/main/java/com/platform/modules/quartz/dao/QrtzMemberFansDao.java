/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberFansDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-09 14:02:22        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.quartz.entity.QrtzMemberFansEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2020-09-09 14:02:22
 */
@Mapper
public interface QrtzMemberFansDao extends BaseMapper<QrtzMemberFansEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QrtzMemberFansEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 查询所有列表
     * @return List
     */
    List<QrtzMemberFansEntity> queryList();

    List<QrtzMemberFansEntity> queryByMemberMessageQuery(@Param("params") Map<String, Object> params);
}
