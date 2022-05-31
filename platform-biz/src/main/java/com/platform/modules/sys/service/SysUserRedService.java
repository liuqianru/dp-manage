/*
 * 项目名称:platform-plus
 * 类名称:SysUserRedService.java
 * 包名称:com.platform.modules.sys.service
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    李鹏军      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.sys.entity.SysUserRegEntity;

import java.util.Map;

/**
 * 系统用户
 *
 * @author 孙
 */

public interface SysUserRedService extends IService<SysUserRegEntity> {

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 保存用户
     *
     * @param userreg 用户
     *
     */
    void add(SysUserRegEntity userreg, Map<String, Object> params);

    /**
     * 修改
     *
     * @param user 用户
     * @param params 查询参数
     */
    void update(SysUserRegEntity user, Map<String, Object> params);


    /**
     * 修改状态
     *
     * @param user 用户
     * @param params 查询参数
     */
    void updateState(SysUserRegEntity user);


    /**
     * 删除用户
     *
     * @param id 用户Ids
     */
    void deleteBatch(String[] ids);


}
