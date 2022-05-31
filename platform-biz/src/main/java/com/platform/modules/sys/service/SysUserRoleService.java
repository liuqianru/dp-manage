/*
 * 项目名称:platform-plus
 * 类名称:SysUserRoleService.java
 * 包名称:com.platform.modules.sys.service
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    李鹏军      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.sys.entity.SysUserRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户与角色对应关系
 *
 * @author 李鹏军
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {
    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysUserRoleEntity> queryAll(Map<String, Object> params);
    /**
     * 新增
     *
     * @param sysUserRoleEntity
     * @return 新增结果
     */
    boolean add(SysUserRoleEntity sysUserRoleEntity);

    /**
     * saveOrUpdate
     *
     * @param userId 用户Id
     * @param roleIdList roleIdList
     */
    void saveOrUpdate(String userId, List<String> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     *
     * @param userId 用户Id
     * @return List
     */
    List<String> queryRoleIdList(String userId);

    List<SysUserRoleEntity> queryRoleList(Map<String, Object> params);

    /**
     * 根据角色ID数组，批量删除
     *
     * @param roleIds roleIds
     * @return int
     */
    int deleteBatch(String[] roleIds);
}
