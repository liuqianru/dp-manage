/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareOtherobjectsDao.java
 * 包名称:com.platform.modules.qkjrts.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-22 14:44:54        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareOtherobjectsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2022-04-22 14:44:54
 */
@Mapper
public interface QkjrtsWelfareOtherobjectsDao extends BaseMapper<QkjrtsWelfareOtherobjectsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrtsWelfareOtherobjectsEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjrtsWelfareOtherobjectsEntity> selectQkjrtsWelfareOtherobjectsPage(IPage page, @Param("params") Map<String, Object> params);

    boolean deleteByMainId(String mainid);
}
