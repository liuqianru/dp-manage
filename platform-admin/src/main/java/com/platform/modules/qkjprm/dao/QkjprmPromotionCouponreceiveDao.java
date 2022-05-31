/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponreceiveDao.java
 * 包名称:com.platform.modules.qkjprm.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:49        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjprm.domain.CouponInfo;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponreceiveEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author hanjie
 * @date 2021-12-09 10:46:49
 */
@Mapper
public interface QkjprmPromotionCouponreceiveDao extends BaseMapper<QkjprmPromotionCouponreceiveEntity> {

    /**
     * 根据状态获取返现券列表
     * * status 0 全部 1 已过期 2 已使用 3 有效
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
    List<QkjprmPromotionCouponreceiveEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjprmPromotionCouponreceiveEntity> selectQkjprmPromotionCouponreceivePage(IPage page, @Param("params") Map<String, Object> params);
}
