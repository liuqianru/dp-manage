/*
 * 项目名称:platform-plus
 * 类名称:QkjmHProcessService.java
 * 包名称:com.platform.modules.qkjm.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-02 16:26:02        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjm.entity.QkjmHProcessEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 孙珊珊
 * @date 2021-11-02 16:26:02
 */
public interface QkjmHProcessService extends IService<QkjmHProcessEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjmHProcessEntity> queryAll(Map<String, Object> params);

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
     * @param qkjmHProcess 
     * @return 新增结果
     */
    boolean add(QkjmHProcessEntity qkjmHProcess);

    /**
     * 根据主键更新
     *
     * @param qkjmHProcess 
     * @return 更新结果
     */
    boolean update(QkjmHProcessEntity qkjmHProcess);

    /**
     * 根据主键删除
     *
     * @param uuid uuid
     * @return 删除结果
     */
    boolean delete(String uuid);

    /**
     * 根据主键批量删除
     *
     * @param uuids uuids
     * @return 删除结果
     */
    boolean deleteBatch(String[] uuids);
}
