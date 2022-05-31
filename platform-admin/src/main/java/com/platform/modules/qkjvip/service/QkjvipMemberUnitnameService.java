/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberUnitnameService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-17 10:44:55        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipMemberUnitnameEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2021-11-17 10:44:55
 */
public interface QkjvipMemberUnitnameService extends IService<QkjvipMemberUnitnameEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberUnitnameEntity> queryAll(Map<String, Object> params);

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
     * @param qkjvipMemberUnitname 
     * @return 新增结果
     */
    boolean add(QkjvipMemberUnitnameEntity qkjvipMemberUnitname);

    /**
     * 根据主键更新
     *
     * @param qkjvipMemberUnitname 
     * @return 更新结果
     */
    boolean update(QkjvipMemberUnitnameEntity qkjvipMemberUnitname);

    /**
     * 根据主键删除
     *
     * @param unitname unitname
     * @return 删除结果
     */
    boolean delete(String unitname);

    /**
     * 根据主键批量删除
     *
     * @param unitnames unitnames
     * @return 删除结果
     */
    boolean deleteBatch(String[] unitnames);
}
