/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareDao.java
 * 包名称:com.platform.modules.qkjrts.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:28        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:28
 */
@Mapper
public interface QkjrtsWelfareDao extends BaseMapper<QkjrtsWelfareEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrtsWelfareEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjrtsWelfareEntity> selectQkjrtsWelfarePage(IPage page, @Param("params") Map<String, Object> params);
}
