/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderWarehouseDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-15 15:49:03        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipOrderWarehouseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 孙珊珊
 * @date 2021-03-15 15:49:03
 */
@Mapper
public interface QkjvipOrderWarehouseDao extends BaseMapper<QkjvipOrderWarehouseEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipOrderWarehouseEntity> queryAll(@Param("params") Map<String, Object> params);
    List<QkjvipOrderWarehouseEntity> queryAllStock(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipOrderWarehouseEntity> selectQkjvipOrderWarehousePage(IPage page, @Param("params") Map<String, Object> params);

    List<QkjvipOrderWarehouseEntity> selectMagWarehousePage(IPage page, @Param("params") Map<String, Object> params);

    List<QkjvipOrderWarehouseEntity> queryAllMag(@Param("params") Map<String, Object> params);

    /**
     * 查询仓库下所有库架及库位
     *
     * @return List
     */
    List<QkjvipOrderWarehouseEntity> queryList(@Param("params") Map<String, Object> params);

    List<QkjvipOrderWarehouseEntity> queryStatisticsList(@Param("params") Map<String, Object> params);

    /**
     * 查询仓库下可以绑定订单的所有库架及库位
     *
     * @return List
     */
    List<QkjvipOrderWarehouseEntity> querySelectList(@Param("params") Map<String, Object> params);

    int updateRoots();
}
