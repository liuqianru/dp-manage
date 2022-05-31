/*
 * 项目名称:platform-plus
 * 类名称:QkjtakeRWineDao.java
 * 包名称:com.platform.modules.qkjtake.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-02-21 14:18:34             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjtake.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjtake.entity.QkjtakeRWineEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 
 * @date 2022-02-21 14:18:34
 */
@Mapper
public interface QkjtakeRWineDao extends BaseMapper<QkjtakeRWineEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjtakeRWineEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjtakeRWineEntity> selectQkjtakeRWinePage(IPage page, @Param("params") Map<String, Object> params);

    boolean updateTakeStateById(QkjtakeRWineEntity qkjtakeRWineEntity);
    boolean updateTakeByWareproid(QkjtakeRWineEntity qkjtakeRWineEntity);

    void updateStateById(String Id);
}
