/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrderDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-11 14:15:23        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 孙珊珊
 * @date 2020-08-11 14:15:23
 */
@Mapper
public interface QkjvipMemberOrderDao extends BaseMapper<QkjvipMemberOrderEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberOrderEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 查询所有orderid
     *
     * @param mbList 查询参数
     * @return List
     */
    List<QkjvipMemberOrderEntity> queryOrderIdList(@Param("mbList") List<QkjvipMemberOrderEntity> mbList);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberOrderEntity> selectQkjvipMemberOrderPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 批量更新
     *
     * @param mbList mbList
     * @return int
     */
    void updateBatchByOrderyId(List<QkjvipMemberOrderEntity> mbList);

    /**
     * 批量删除
     *
     * @param mbList mbList
     * @return int
     */
    void deleteBatchByOrderyId(List<QkjvipMemberOrderEntity> mbList);

    /**
     * 根据角色ID数组，批量删除
     *
     * @param mbList 角色Ids
     * @return int
     */
    int deleteBatchByOrder(List<QkjvipMemberOrderEntity> mbList);

    int removeByVisitId(String visitId);
    boolean removeByVisitIds(String[] visitIds);
}
