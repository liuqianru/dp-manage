/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponService.java
 * 包名称:com.platform.modules.qkjprm.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:49        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author hanjie
 * @date 2021-12-09 10:46:49
 */
public interface QkjprmPromotionCouponService extends IService<QkjprmPromotionCouponEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjprmPromotionCouponEntity> queryAll(Map<String, Object> params);

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
     * @param qkjprmPromotionCoupon
     * @return 新增结果
     */
    boolean add(QkjprmPromotionCouponEntity qkjprmPromotionCoupon);

    /**
     * 根据主键更新
     *
     * @param qkjprmPromotionCoupon
     * @return 更新结果
     */
    boolean update(QkjprmPromotionCouponEntity qkjprmPromotionCoupon);

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
     * 获取有效的优惠券列表
     *
     * @return
     */
    List<QkjprmPromotionCouponEntity> getVaildList(String memberunionid);

    /**
     * 根据领取记录id获取优惠券信息
     *
     * @param receiveRecordId
     * @return
     */
    QkjprmPromotionCouponEntity getCouponByReceive(String receiveRecordId);

    /**
     * 根据记录id获取数据 加锁
     * @param id
     * @return
     */
    QkjprmPromotionCouponEntity getLock(String id);

    /**
     * 更新库存
     *
     * @param params
     * @return
     */
    boolean updateStock(@Param("params") Map<String, Object> params);
}
