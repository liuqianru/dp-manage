/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponServiceImpl.java
 * 包名称:com.platform.modules.qkjprm.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:49        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjprm.dao.QkjprmPromotionCouponDao;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author hanjie
 * @date 2021-12-09 10:46:49
 */
@Service("qkjprmPromotionCouponService")
public class QkjprmPromotionCouponServiceImpl extends ServiceImpl<QkjprmPromotionCouponDao, QkjprmPromotionCouponEntity> implements QkjprmPromotionCouponService {

    @Override
    public List<QkjprmPromotionCouponEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjprmPromotionCouponEntity> page = new Query<QkjprmPromotionCouponEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjprmPromotionCouponPage(page, params));
    }

    @Override
    public boolean add(QkjprmPromotionCouponEntity qkjprmPromotionCoupon) {
        return this.save(qkjprmPromotionCoupon);
    }

    @Override
    public boolean update(QkjprmPromotionCouponEntity qkjprmPromotionCoupon) {
        return this.updateById(qkjprmPromotionCoupon);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    /**
     * 获取有效的优惠券列表
     * <p>
     * memberunionid 当前人unionid
     *
     * @return
     */
    public List<QkjprmPromotionCouponEntity> getVaildList(String memberunionid) {
        return baseMapper.getVaildList(memberunionid);
    }

    /**
     * 根据领取记录id获取优惠券信息
     *
     * @param receiveRecordId
     * @return
     */
    public QkjprmPromotionCouponEntity getCouponByReceive(String receiveRecordId) {
        return baseMapper.getCouponByReceive(receiveRecordId);
    }

    /**
     * 根据记录id获取数据 加锁
     *
     * @param id
     * @return
     */
    public QkjprmPromotionCouponEntity getLock(String id) {
        return baseMapper.getLock(id);
    }

    /**
     * 更新库存
     *
     * @param params
     * @return
     */
    public boolean updateStock(@Param("params") Map<String, Object> params) {
      return baseMapper.updateStock(params);
    }
}
