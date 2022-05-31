/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponreceiveService.java
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
import com.platform.modules.qkjprm.domain.CouponInfo;
import com.platform.modules.qkjprm.domain.ReceiveParam;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponreceiveEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author hanjie
 * @date 2021-12-09 10:46:49
 */
public interface QkjprmPromotionCouponreceiveService extends IService<QkjprmPromotionCouponreceiveEntity> {

    /**
     * 领取优惠券
     *
     * @param param
     * @return
     */
    QkjprmPromotionCouponreceiveEntity receiveCoupon(ReceiveParam param) throws Exception;

    /**
     * 根据状态获取返现券数量
     * status 0 全部 1 已过期 2 已使用 3 有效
     *
     * @param params
     * @return
     */
    List<CouponInfo> getCouponList(@Param("params") Map<String, Object> params);

    /**
     * 根据状态获取返现券数量
     * status 0 全部 1 已过期 2 已使用 3 有效
     *
     * @param params
     * @return
     */
    int getCouponCount(@Param("params") Map<String, Object> params);

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjprmPromotionCouponreceiveEntity> queryAll(Map<String, Object> params);

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
     * @param qkjprmPromotionCouponreceive
     * @return 新增结果
     */
    boolean add(QkjprmPromotionCouponreceiveEntity qkjprmPromotionCouponreceive);

    /**
     * 根据主键更新
     *
     * @param qkjprmPromotionCouponreceive
     * @return 更新结果
     */
    boolean update(QkjprmPromotionCouponreceiveEntity qkjprmPromotionCouponreceive);

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
