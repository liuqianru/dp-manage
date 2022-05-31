/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivitymbsService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-18 13:35:24        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivitymbsEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 孙珊珊
 * @date 2020-09-18 13:35:24
 */
public interface QkjvipMemberActivitymbsService extends IService<QkjvipMemberActivitymbsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberActivitymbsEntity> queryAll(Map<String, Object> params);
    List<QkjvipMemberActivitymbsEntity> queryAllCount(Map<String, Object> params);
    List<QkjvipMemberActivitymbsEntity> queryTopOne(Map<String, Object> params);

    List<QkjvipMemberActivitymbsEntity> queryAllResultExcel(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);
    Page queryPageCount(Map<String, Object> params);
    Page queryPageByPersonal(Map<String, Object> params);

    /**
     * 新增
     *
     * @param qkjvipMemberActivitymbs 
     * @return 新增结果
     */
    boolean add(QkjvipMemberActivitymbsEntity qkjvipMemberActivitymbs);

    /**
     * 根据主键更新
     *
     * @param qkjvipMemberActivitymbs 
     * @return 更新结果
     */
    boolean update(QkjvipMemberActivitymbsEntity qkjvipMemberActivitymbs);

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
     * 批量添加
     *
     * @param qkjvipMemberActivitymbs 用户
     */
    void batchAdd(List<QkjvipMemberActivitymbsEntity> qkjvipMemberActivitymbs);
    /**
     * 批量更新
     *
     * @param qkjvipMemberActivitymbs 用户
     */
    void batchUpdate(List<QkjvipMemberActivitymbsEntity> qkjvipMemberActivitymbs);


    int deleteBatchByOrder(String activityId);

    void supadd(String activity,String member_id);
    /**
     * 查询所有列表
     *
     * @param activityId 查询参数
     * @return List
     */
    List<QkjvipMemberMessageUserQueryEntity> queryByActivityId(String activityId);

}
