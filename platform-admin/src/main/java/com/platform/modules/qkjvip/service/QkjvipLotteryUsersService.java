/*
 * 项目名称:platform-plus
 * 类名称:QkjvipLotteryUsersService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-05 10:59:58        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipLotteryUsersEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2021-07-05 10:59:58
 */
public interface QkjvipLotteryUsersService extends IService<QkjvipLotteryUsersEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipLotteryUsersEntity> queryAll(Map<String, Object> params);
    List<QkjvipLotteryUsersEntity> queryLotteryUsers(Map<String, Object> params);
    List<QkjvipLotteryUsersEntity> queryWinners(Map<String, Object> params);

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
     * @param qkjvipLotteryUsers 
     * @return 新增结果
     */
    boolean add(QkjvipLotteryUsersEntity qkjvipLotteryUsers);
    boolean addBatch(List<QkjvipLotteryUsersEntity> userList);

    /**
     * 根据主键更新
     *
     * @param qkjvipLotteryUsers 
     * @return 更新结果
     */
    boolean update(QkjvipLotteryUsersEntity qkjvipLotteryUsers);
    boolean updateBatch(List<QkjvipLotteryUsersEntity> userList);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);
    boolean deleteByMainId(String mainid);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);
}
