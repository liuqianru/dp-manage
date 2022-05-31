/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponexchangeService.java
 * 包名称:com.platform.modules.qkjprm.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjprm.domain.ScanCodeInfo;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponexchangeEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author hanjie
 * @date 2021-12-09 10:46:48
 */
public interface QkjprmPromotionCouponexchangeService extends IService<QkjprmPromotionCouponexchangeEntity> {

    /**
     * 获取扫码的用户
     *
     * @param bottlecode
     * @return
     */
    List<String> getScanUnion(String bottlecode);

    /**
     * 获取云码扫码记录
     *
     * @param code
     * @return
     */
    ScanCodeInfo getYMScanInfo(String code);

    /**
     * 扫码兑换优惠券
     *
     * @param bottlecode
     * @param unionid
     * @param couponsn
     * @return
     * @throws Exception
     */
    boolean exchangeScanCode(String bottlecode, String unionid, String couponsn) throws Exception;

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjprmPromotionCouponexchangeEntity> queryAll(Map<String, Object> params);

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
     * @param qkjprmPromotionCouponexchange
     * @return 新增结果
     */
    boolean add(QkjprmPromotionCouponexchangeEntity qkjprmPromotionCouponexchange);

    /**
     * 根据主键更新
     *
     * @param qkjprmPromotionCouponexchange
     * @return 更新结果
     */
    boolean update(QkjprmPromotionCouponexchangeEntity qkjprmPromotionCouponexchange);

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
