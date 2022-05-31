/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntegralService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-21 15:18:24        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntegralEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2020-12-21 15:18:24
 */
public interface QkjvipMemberIntegralService extends IService<QkjvipMemberIntegralEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberIntegralEntity> queryAll(Map<String, Object> params);

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
     * @param qkjvipMemberIntegral 
     * @return 新增结果
     */
    void add(QkjvipMemberIntegralEntity qkjvipMemberIntegral);

    /**
     * 根据主键更新
     *
     * @param qkjvipMemberIntegral 
     * @return 更新结果
     */
    void update(QkjvipMemberIntegralEntity qkjvipMemberIntegral);

    void updateStatus(QkjvipMemberIntegralEntity qkjvipMemberIntegral);

    void sendIntegral(QkjvipMemberIntegralEntity qkjvipMemberIntegral, List<String> memberids) throws IOException;

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
    void deleteBatch(String[] ids);
}
