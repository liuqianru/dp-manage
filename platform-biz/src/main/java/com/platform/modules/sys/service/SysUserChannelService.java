/*
 * 项目名称:platform-plus
 * 类名称:SysUserChannelService.java
 * 包名称:com.platform.modules.sys.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-09 11:32:32        李鹏军     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.sys.entity.SysUserChannelEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 李鹏军
 * @date 2020-12-09 11:32:32
 */
public interface SysUserChannelService extends IService<SysUserChannelEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysUserChannelEntity> queryAll(Map<String, Object> params);

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
     * @param sysUserChannel 
     * @return 新增结果
     */
    boolean add(SysUserChannelEntity sysUserChannel);

    /**
     * 根据主键更新
     *
     * @param sysUserChannel 
     * @return 更新结果
     */
    boolean update(SysUserChannelEntity sysUserChannel);

    /**
     * saveOrUpdate
     *
     * @param userId 用户Id
     * @param channelIdList channelIdList
     */
    void saveOrUpdate(String userId, List<Integer> channelIdList);

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
     * 根据用户ID，获取渠道ID列表
     *
     * @param userId 用户Id
     * @return List
     */
    List<Integer> queryChannelIdList(String userId);

    String queryChannelIdByUserId(String userId);

    List<SysUserChannelEntity> queryPermissionChannels(Map<String, Object> params);
}
