/*
 * 项目名称:platform-plus
 * 类名称:QkjmHProcessDao.java
 * 包名称:com.platform.modules.qkjm.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-02 16:26:02        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjm.entity.QkjmHProcessEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 孙珊珊
 * @date 2021-11-02 16:26:02
 */
@Mapper
public interface QkjmHProcessDao extends BaseMapper<QkjmHProcessEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjmHProcessEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjmHProcessEntity> selectQkjmHProcessPage(IPage page, @Param("params") Map<String, Object> params);
}
