/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportActivitytempService.java
 * 包名称:com.platform.modules.qkjrpt.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 13:05:30        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjrpt.entity.QkjrptReportActivitytempEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 孙珊珊
 * @date 2021-09-15 13:05:30
 */
public interface QkjrptReportActivitytempService extends IService<QkjrptReportActivitytempEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrptReportActivitytempEntity> queryAll(Map<String, Object> params);

    List<QkjrptReportActivitytempEntity> queryAllRpt(Map<String, Object> params);
    List<QkjrptReportActivitytempEntity> queryAllRptTs(Map<String, Object> params);

    List<QkjrptReportActivitytempEntity> queryAllTypeRpt(Map<String, Object> params);

    List<QkjrptReportActivitytempEntity> queryAllRptSource(Map<String, Object> params);

    List<QkjrptReportActivitytempEntity> queryAllRptTotal(Map<String, Object> params);

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
     * @param qkjrptReportActivitytemp 
     * @return 新增结果
     */
    boolean add(QkjrptReportActivitytempEntity qkjrptReportActivitytemp);

    /**
     * 根据主键更新
     *
     * @param qkjrptReportActivitytemp 
     * @return 更新结果
     */
    boolean update(QkjrptReportActivitytempEntity qkjrptReportActivitytemp);

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
