/*
 * 项目名称:platform-plus
 * 类名称:SysOrgUpdatelogService.java
 * 包名称:com.platform.modules.sys.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 13:52:13        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.sys.entity.SysOrgUpdatelogEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 孙珊珊
 * @date 2021-08-04 13:52:13
 */
public interface SysOrgUpdatelogService extends IService<SysOrgUpdatelogEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysOrgUpdatelogEntity> queryAll(Map<String, Object> params);

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
     * @param sysOrgUpdatelog 
     * @return 新增结果
     */
    boolean add(SysOrgUpdatelogEntity sysOrgUpdatelog);

    /**
     * 根据主键更新
     *
     * @param sysOrgUpdatelog 
     * @return 更新结果
     */
    boolean update(SysOrgUpdatelogEntity sysOrgUpdatelog);

    /**
     * 根据主键删除
     *
     * @param deptcode deptcode
     * @return 删除结果
     */
    boolean delete(String deptcode);

    /**
     * 根据主键批量删除
     *
     * @param deptcodes deptcodes
     * @return 删除结果
     */
    boolean deleteBatch(String[] deptcodes);

    void batchAdd(List<SysOrgUpdatelogEntity> users);
}
