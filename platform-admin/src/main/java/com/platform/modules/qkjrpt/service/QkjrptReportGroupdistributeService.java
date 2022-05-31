/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportGroupdistributeService.java
 * 包名称:com.platform.modules.qkjrpt.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-11 15:11:18        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjrpt.domain.QkjrptGroupDistributeStatic;
import com.platform.modules.qkjrpt.entity.QkjrptReportGroupdistributeEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author hanjie
 * @date 2021-11-11 15:11:18
 */
public interface QkjrptReportGroupdistributeService extends IService<QkjrptReportGroupdistributeEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportGroupdistributeEntity> queryAll(Map<String, Object> params);

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
     * @param qkjrptReportGroupdistribute
     * @return 新增结果
     */
    void add(QkjrptReportGroupdistributeEntity qkjrptReportGroupdistribute);

    /**
     * 根据主键更新
     *
     * @param qkjrptReportGroupdistribute
     * @return 更新结果
     */
    void update(QkjrptReportGroupdistributeEntity qkjrptReportGroupdistribute);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    void delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    void deleteBatch(String[] ids);

    /**
     * 查询经销商区域统计数据
     * @param params
     * @return
     */
    List<QkjrptGroupDistributeStatic> getPrimaryAreaDst(Map<String,Object> params);
}
