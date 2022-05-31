/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareOtherobjectsService.java
 * 包名称:com.platform.modules.qkjrts.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-22 14:44:54        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareOtherobjectsEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2022-04-22 14:44:54
 */
public interface QkjrtsWelfareOtherobjectsService extends IService<QkjrtsWelfareOtherobjectsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrtsWelfareOtherobjectsEntity> queryAll(Map<String, Object> params);

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
     * @param qkjrtsWelfareOtherobjects 
     * @return 新增结果
     */
    boolean add(QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects);
    boolean addBatch(List<QkjrtsWelfareOtherobjectsEntity> list);

    /**
     * 根据主键更新
     *
     * @param qkjrtsWelfareOtherobjects 
     * @return 更新结果
     */
    boolean update(QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);
    boolean deleteByMainId(String mainid);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);
    /**
     * 发送
     *
     * @param qkjrtsWelfareOtherobjects qkjrtsWelfareOtherobjects
     */
    void send(QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects);
}
