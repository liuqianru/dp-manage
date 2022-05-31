/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportActivitypeoplanService.java
 * 包名称:com.platform.modules.qkjrpt.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-28 11:31:01        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjrpt.entity.QkjrptReportActivitypeoplanEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 孙珊珊
 * @date 2021-09-28 11:31:01
 */
public interface QkjrptReportActivitypeoplanService extends IService<QkjrptReportActivitypeoplanEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportActivitypeoplanEntity> queryAll(Map<String, Object> params);

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
     * @param qkjrptReportActivitypeoplan 
     * @return 新增结果
     */
    boolean add(QkjrptReportActivitypeoplanEntity qkjrptReportActivitypeoplan);

    /**
     * 根据主键更新
     *
     * @param qkjrptReportActivitypeoplan 
     * @return 更新结果
     */
    boolean update(QkjrptReportActivitypeoplanEntity qkjrptReportActivitypeoplan);

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
