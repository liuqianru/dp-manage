/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberDatadepService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 17:33:07        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipMemberDatadepEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2021-08-04 17:33:07
 */
public interface QkjvipMemberDatadepService extends IService<QkjvipMemberDatadepEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberDatadepEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * queryByOrgNo
     *
     * @param orgNo 部门号
     * @return QkjvipMemberDatadepEntity
     */
    QkjvipMemberDatadepEntity queryByOrgNo(String orgNo);

    /**
     * 新增
     *
     * @param qkjvipMemberDatadep 
     * @return 新增结果
     */
    boolean add(QkjvipMemberDatadepEntity qkjvipMemberDatadep);

    /**
     * 根据主键更新
     *
     * @param qkjvipMemberDatadep 
     * @return 更新结果
     */
    boolean update(QkjvipMemberDatadepEntity qkjvipMemberDatadep);

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
}
