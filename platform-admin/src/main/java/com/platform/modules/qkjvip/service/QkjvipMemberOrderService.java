/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrderService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-11 14:15:23        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrderEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 孙珊珊
 * @date 2020-08-11 14:15:23
 */
public interface QkjvipMemberOrderService extends IService<QkjvipMemberOrderEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberOrderEntity> queryAll(Map<String, Object> params);

    /**
     * 查询所有列表
     *
     * @param mbList 查询参数
     * @return List
     */
    List<QkjvipMemberOrderEntity> queryOrderIdList(List<QkjvipMemberOrderEntity> mbList);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增
     *
     * @param qkjvipMemberOrder 
     * @return 新增结果
     */
    boolean add(QkjvipMemberOrderEntity qkjvipMemberOrder);

    void addBatch(List<QkjvipMemberOrderEntity> memberOrderList);
    /**
     * 根据主键更新
     *
     * @param qkjvipMemberOrder 
     * @return 更新结果
     */
    boolean update(QkjvipMemberOrderEntity qkjvipMemberOrder);

    /**
     * 批量增加
     *
     * @param mbList 用户
     */
    void saveBatch(List<QkjvipMemberOrderEntity> mbList);

    /**
     * 批量修改
     *
     * @param mbList mbList
     */
    void updateBatch(List<QkjvipMemberOrderEntity> mbList);

    /**
     * 批量删除
     *
     * @param mbList mbList
     */
    void deleteBatch(List<QkjvipMemberOrderEntity> mbList);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);

    /**
     * 根据角色ID数组，批量删除
     *
     * @param mbList mbList
     * @return int
     */
    int deleteBatchByOrder(List<QkjvipMemberOrderEntity> mbList);

}
