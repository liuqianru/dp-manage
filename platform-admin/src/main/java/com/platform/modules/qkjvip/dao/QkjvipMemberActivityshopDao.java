/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivityshopDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-22 16:21:27             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivityshopEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 
 * @date 2022-04-22 16:21:27
 */
@Mapper
public interface QkjvipMemberActivityshopDao extends BaseMapper<QkjvipMemberActivityshopEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberActivityshopEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberActivityshopEntity> selectQkjvipMemberActivityshopPage(IPage page, @Param("params") Map<String, Object> params);

    int deleteBatchByMainId(@Param("activityId") String activityId);
}
