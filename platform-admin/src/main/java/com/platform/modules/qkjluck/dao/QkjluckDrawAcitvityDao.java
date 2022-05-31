/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawAcitvityDao.java
 * 包名称:com.platform.modules.qkjluck.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-05 17:26:24        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjluck.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjluck.entity.QkjluckDrawAcitvityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 孙珊珊
 * @date 2021-07-05 17:26:24
 */
@Mapper
public interface QkjluckDrawAcitvityDao extends BaseMapper<QkjluckDrawAcitvityEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjluckDrawAcitvityEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjluckDrawAcitvityEntity> selectQkjluckDrawAcitvityPage(IPage page, @Param("params") Map<String, Object> params);
}
