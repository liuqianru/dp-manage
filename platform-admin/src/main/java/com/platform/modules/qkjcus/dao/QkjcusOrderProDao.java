/*
 * 项目名称:platform-plus
 * 类名称:QkjcusOrderProDao.java
 * 包名称:com.platform.modules.qkjcus.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-23 16:50:14             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjcus.entity.QkjcusOrderProEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 
 * @date 2021-12-23 16:50:14
 */
@Mapper
public interface QkjcusOrderProDao extends BaseMapper<QkjcusOrderProEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjcusOrderProEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjcusOrderProEntity> selectQkjcusOrderProPage(IPage page, @Param("params") Map<String, Object> params);
}
