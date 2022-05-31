/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareService.java
 * 包名称:com.platform.modules.qkjrts.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:28        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:28
 */
public interface QkjrtsWelfareService extends IService<QkjrtsWelfareEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrtsWelfareEntity> queryAll(Map<String, Object> params);

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
     * @param qkjrtsWelfare 
     * @return 新增结果
     */
    void add(QkjrtsWelfareEntity qkjrtsWelfare);

    /**
     * 根据主键更新
     *
     * @param qkjrtsWelfare 
     * @return 更新结果
     */
    void update(QkjrtsWelfareEntity qkjrtsWelfare);
    void mdy(QkjrtsWelfareEntity qkjrtsWelfare);

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
    /**
     * 发送
     *
     * @param id id
     * @return 删除结果
     */
    void send(String id);
}
