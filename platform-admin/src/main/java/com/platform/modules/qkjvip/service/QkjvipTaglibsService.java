/*
 * 项目名称:platform-plus
 * 类名称:QkjvipTaglibsService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-26 14:20:07        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipTaglibsEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2020-08-26 14:20:07
 */
public interface QkjvipTaglibsService extends IService<QkjvipTaglibsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipTaglibsEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);
    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipTaglibsEntity> queryToCheck(Map<String, Object> params);

    /**
     * 新增
     *
     * @param qkjvipTaglibs 
     * @return 新增结果
     */
    boolean add(QkjvipTaglibsEntity qkjvipTaglibs);

    /**
     * 根据主键更新
     *
     * @param qkjvipTaglibs 
     * @return 更新结果
     */
    boolean update(QkjvipTaglibsEntity qkjvipTaglibs);

    /**
     * 根据主键删除
     *
     * @param labelId labelId
     * @return 删除结果
     */
    boolean delete(String labelId);

    /**
     * 根据主键批量删除
     *
     * @param labelIds labelIds
     * @return 删除结果
     */
    boolean deleteBatch(String[] labelIds);
}
