/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponDao.java
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
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author hanjie
 * @date 2021-12-09 10:46:49
 */
@Mapper
public interface QkjprmPromotionCouponDao extends BaseMapper<QkjprmPromotionCouponEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjprmPromotionCouponEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjprmPromotionCouponEntity> selectQkjprmPromotionCouponPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 获取有效的优惠券列表
     *
     * @return
     */
    List<QkjprmPromotionCouponEntity> getVaildList(@Param("memberunionid") String memberunionid);


    /**
     * 根据领取记录id获取优惠券信息
     *
     * @param receiveRecordId
     * @return
     */
    QkjprmPromotionCouponEntity getCouponByReceive(String receiveRecordId);

    /**
     * 根据记录id获取数据 加锁
     *
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
