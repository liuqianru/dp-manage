/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivitymbsDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-18 13:35:24        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivitymbsEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 孙珊珊
 * @date 2020-09-18 13:35:24
 */
@Mapper
public interface QkjvipMemberActivitymbsDao extends BaseMapper<QkjvipMemberActivitymbsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberActivitymbsEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 查询是否存在
     * @param params
     * @return
     */
    List<QkjvipMemberActivitymbsEntity> queryTopOne(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberActivitymbsEntity> selectQkjvipMemberActivitymbsPage(IPage page, @Param("params") Map<String, Object> params);
    List<QkjvipMemberActivitymbsEntity> selectQkjvipMemberActivitymbsPageCount(IPage page, @Param("params") Map<String, Object> params);
    List<QkjvipMemberActivitymbsEntity> queryAllCount(@Param("params") Map<String, Object> params);
    List<QkjvipMemberActivitymbsEntity> queryAllResultExcel(@Param("params") Map<String, Object> params);
    int deleteBatchByOrder(String activityId);
    List<QkjvipMemberMessageUserQueryEntity> queryByActivityId(String activityId);

    List<QkjvipMemberActivitymbsEntity> selectPerQkjvipMemberActivityPage(IPage page, @Param("params") Map<String, Object> params);
}
