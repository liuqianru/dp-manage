/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponreceiveServiceImpl.java
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
import com.platform.modules.qkjprm.dao.QkjprmPromotionCouponreceiveDao;
import com.platform.modules.qkjprm.domain.CouponInfo;
import com.platform.modules.qkjprm.domain.ReceiveParam;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponEntity;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponreceiveEntity;
import com.platform.modules.qkjprm.entity.QkjprmPromotionMemberEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponService;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponreceiveService;
import com.platform.modules.qkjprm.service.QkjprmPromotionMemberService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

/**
 * Service实现类
 *
 * @author hanjie
 * @date 2021-12-09 10:46:49
 */
@Service("qkjprmPromotionCouponreceiveService")
public class QkjprmPromotionCouponreceiveServiceImpl extends ServiceImpl<QkjprmPromotionCouponreceiveDao, QkjprmPromotionCouponreceiveEntity> implements QkjprmPromotionCouponreceiveService {

    @Autowired
    private QkjprmPromotionCouponService couponService;
    @Autowired
    private QkjprmPromotionMemberService memberService;

    /**
     * 领取优惠券
     *
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public QkjprmPromotionCouponreceiveEntity receiveCoupon(ReceiveParam param) throws Exception {

        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("unionid", param.getUnionid());
        //添加用户信息
        List<QkjprmPromotionMemberEntity> memberList = memberService.queryAll(queryParam);
        if (memberList == null || memberList.size() == 0) {
            QkjprmPromotionMemberEntity memberEntity = new QkjprmPromotionMemberEntity();
            memberEntity.setNickname(param.getNickname());
            memberEntity.setAssistintegral(0);
            memberEntity.setHeadimg(param.getHeadimg());
            memberEntity.setUnionid(param.getUnionid());
            memberEntity.setOpenid(param.getOpenid());
            memberEntity.setExchangeamount(BigDecimal.ZERO);
            memberEntity.setProductintegral(0);
            memberEntity.setSex(param.getSex() == null ? 0 : param.getSex());
            //添加用户失败 回滚事务
            if (!memberService.add(memberEntity)) {
                throw new Exception("添加用户失败");
            }
        }

        QkjprmPromotionCouponEntity couponEntity = couponService.getLock(param.getCouponid());
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, -1);
        if (couponEntity == null || couponEntity.getStock() <= 0 ||
                (couponEntity.getActionenddate() != null && couponEntity.getActionstartdate().after(calendar.getTime()))) {
            throw new Exception("优惠券不存在");
        }
        //添加领取信息
        QkjprmPromotionCouponreceiveEntity entity = new QkjprmPromotionCouponreceiveEntity();
        entity.setNickname(param.getNickname());
        entity.setCouponid(param.getCouponid());
        entity.setHeadimg(param.getHeadimg());
        entity.setUnionid(param.getUnionid());
        entity.setOpenid(param.getOpenid());
        entity.setAssistcount(couponEntity.getAssistcount());
        entity.setIsdouble(0);
        entity.setCouponamount(couponEntity.getCouponamount());
        entity.setSex(param.getSex() == null ? 0 : param.getSex());

        //添加失败 回滚事务
        if (!this.save(entity)) {
            throw new Exception("领取失败");
        }
        //减除优惠券库存
        Map<String, Object> updateParam = new HashMap<>();
        updateParam.put("id", couponEntity.getId());
        updateParam.put("num", 1);
        if (!couponService.updateStock(updateParam)) {
            throw new Exception("库存更新失败");
        }
        return entity;
    }

    /**
     * 获取个人优惠券列表
     * status 0 全部 1 已过期 2 已使用 3 有效
     *
     * @param params
     * @return
     */
    public List<CouponInfo> getCouponList(@Param("params") Map<String, Object> params) {
        return baseMapper.getCouponList(params);
    }

    /**
     * 根据状态获取返现券数量
     * status 0 全部 1 已过期 2 已使用 3 有效
     *
     * @param params
     * @return
     */
    public int getCouponCount(@Param("params") Map<String, Object> params) {
        return baseMapper.getCouponCount(params);
    }


    @Override
    public List<QkjprmPromotionCouponreceiveEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.createtime");
        params.put("asc", false);
        Page<QkjprmPromotionCouponreceiveEntity> page = new Query<QkjprmPromotionCouponreceiveEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjprmPromotionCouponreceivePage(page, params));
    }

    @Override
    public boolean add(QkjprmPromotionCouponreceiveEntity qkjprmPromotionCouponreceive) {
        return this.save(qkjprmPromotionCouponreceive);
    }

    @Override
    public boolean update(QkjprmPromotionCouponreceiveEntity qkjprmPromotionCouponreceive) {
        return this.updateById(qkjprmPromotionCouponreceive);
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


}
