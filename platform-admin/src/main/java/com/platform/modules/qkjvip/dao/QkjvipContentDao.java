/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-23 16:37:04        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipContentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2021-03-23 16:37:04
 */
@Mapper
public interface QkjvipContentDao extends BaseMapper<QkjvipContentEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipContentEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipContentEntity> selectQkjvipContentPage(IPage page, @Param("params") Map<String, Object> params);

    List<QkjvipContentEntity> queryByGroupId(String groupId);
}
