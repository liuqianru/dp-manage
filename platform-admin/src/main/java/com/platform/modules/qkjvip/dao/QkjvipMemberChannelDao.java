/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberChannelDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-09 10:55:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipMemberChannelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2020-12-09 10:55:27
 */
@Mapper
public interface QkjvipMemberChannelDao extends BaseMapper<QkjvipMemberChannelEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberChannelEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberChannelEntity> selectQkjvipMemberChannelPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 根据条件查询（不传page则不分页）
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberChannelEntity> selectQkjvipMemberChannelPage(@Param("params") Map<String, Object> params);

}
