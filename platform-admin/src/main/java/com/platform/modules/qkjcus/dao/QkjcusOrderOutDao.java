/*
 * 项目名称:platform-plus
 * 类名称:QkjcusOrderOutDao.java
 * 包名称:com.platform.modules.qkjcus.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-28 10:14:18             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjcus.entity.QkjcusOrderOutEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 
 * @date 2021-12-28 10:14:18
 */
@Mapper
public interface QkjcusOrderOutDao extends BaseMapper<QkjcusOrderOutEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjcusOrderOutEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjcusOrderOutEntity> selectQkjcusOrderOutPage(IPage page, @Param("params") Map<String, Object> params);
}
