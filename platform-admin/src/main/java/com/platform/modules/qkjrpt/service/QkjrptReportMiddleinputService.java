/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportMiddleinputService.java
 * 包名称:com.platform.modules.qkjrpt.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-16 09:36:36        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjrpt.entity.QkjrptReportMiddleinputEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 孙珊珊
 * @date 2021-11-16 09:36:36
 */
public interface QkjrptReportMiddleinputService extends IService<QkjrptReportMiddleinputEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportMiddleinputEntity> queryAll(Map<String, Object> params);

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
     * @param qkjrptReportMiddleinput 
     * @return 新增结果
     */
    boolean add(QkjrptReportMiddleinputEntity qkjrptReportMiddleinput);

    /**
     * 新增
     *
     * @param qkjrptReportMiddleinput
     * @return 新增结果
     */
    // boolean addMiddleinput(QkjrptReportMiddleinputEntity qkjrptReportMiddleinput);

    /**
     * 根据主键更新
     *
     * @param qkjrptReportMiddleinput 
     * @return 更新结果
     */
    boolean update(QkjrptReportMiddleinputEntity qkjrptReportMiddleinput);

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
