/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntegraluserService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-21 15:18:24        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntegraluserEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2020-12-21 15:18:24
 */
public interface QkjvipMemberIntegraluserService extends IService<QkjvipMemberIntegraluserEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberIntegraluserEntity> queryAll(Map<String, Object> params);

    /**
     * 查询所有列表
     *
     * @param integralId 查询参数
     * @return List
     */
    List<QkjvipMemberMessageUserQueryEntity> queryByIntegralId(String integralId);

    /**
     * 查询所有列表
     *
     * @param integralId 查询参数
     * @return List
     */
    List<String> queryByIntegralId2(String integralId);

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
     * @param qkjvipMemberIntegraluser 
     * @return 新增结果
     */
    boolean add(QkjvipMemberIntegraluserEntity qkjvipMemberIntegraluser);

    /**
     * 批量添加
     *
     * @param qkjvipMemberIntegralusers 用户
     */
    void addBatch(List<QkjvipMemberIntegraluserEntity> qkjvipMemberIntegralusers);

    /**
     * 根据主键更新
     *
     * @param qkjvipMemberIntegraluser 
     * @return 更新结果
     */
    boolean update(QkjvipMemberIntegraluserEntity qkjvipMemberIntegraluser);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据integralId删除
     */
    void deleteByIntegralId(String integralId);

    /**
     * 根据integralId批量删除
     */
    void deleteBatchByIntegralId(String[] integralIds);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);
}
