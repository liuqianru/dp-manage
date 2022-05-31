/*
 * 项目名称:platform-plus
 * 类名称:QkjvipProductStockService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-15 15:49:03        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipProductStockEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 孙珊珊
 * @date 2021-03-15 15:49:03
 */
public interface QkjvipProductStockService extends IService<QkjvipProductStockEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipProductStockEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 分页查询现存产品
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPageStock(Map<String, Object> params);
    List<QkjvipProductStockEntity> queryAllDetail(Map<String, Object> params);
    /**
     * 新增
     *
     * @param qkjvipProductStock 
     * @return 新增结果
     */
    boolean add(QkjvipProductStockEntity qkjvipProductStock);
    boolean updateShiftOrderStock(QkjvipProductStockEntity qkjvipProductStock);

    /**
     * 根据主键更新
     *
     * @param qkjvipProductStock 
     * @return 更新结果
     */
    boolean update(QkjvipProductStockEntity qkjvipProductStock);

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

    /**
     * 批量添加
     *
     * @param qkjviporderdetails 用户
     */
    void batchAdd(List<QkjvipProductStockEntity> qkjviporderdetails);
    int deleteBatchByOrder(String morderid);
}
