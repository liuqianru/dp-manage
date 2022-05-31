/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionAssistrecordServiceImpl.java
 * 包名称:com.platform.modules.qkjprm.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjprm.dao.QkjprmPromotionAssistrecordDao;
import com.platform.modules.qkjprm.domain.ReceiveParam;
import com.platform.modules.qkjprm.entity.QkjprmPromotionAssistrecordEntity;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponEntity;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponreceiveEntity;
import com.platform.modules.qkjprm.entity.QkjprmPromotionMemberEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionAssistrecordService;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponService;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponreceiveService;
import com.platform.modules.qkjprm.service.QkjprmPromotionMemberService;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.HsqlMaxValueIncrementer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Service实现类
 *
 * @author hanjie
 * @date 2021-12-09 10:46:48
 */
@Service("qkjprmPromotionAssistrecordService")
public class QkjprmPromotionAssistrecordServiceImpl extends ServiceImpl<QkjprmPromotionAssistrecordDao, QkjprmPromotionAssistrecordEntity> implements QkjprmPromotionAssistrecordService {
    @Autowired
    private QkjprmPromotionCouponService couponService;
    @Autowired
    private QkjprmPromotionCouponreceiveService receiveService;
    @Autowired
    private QkjprmPromotionMemberService memberService;

    /**
     * 助力优惠券翻倍
     *
     * @param assist   助力人信息
     * @param couponsn 助力优惠券编号
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean assistCoupon(ReceiveParam assist, String couponsn) throws Exception {
        try {
            HashMap<String, Object> queryParam = new HashMap<>();
            queryParam.put("couponsn", couponsn);
            List<QkjprmPromotionAssistrecordEntity> list = queryAll(queryParam);
            QkjprmPromotionCouponEntity couponEntity = couponService.getCouponByReceive(couponsn);
            QkjprmPromotionCouponreceiveEntity receiveEntity = receiveService.getById(couponsn);
            if (receiveEntity == null) {
                throw new Exception("优惠券不存在");
            }
            SimpleDateFormat fmt=new SimpleDateFormat("yyyyMMdd");
            if (!fmt.format(receiveEntity.getCreatetime()).equals(fmt.format(new Date())))
                throw new Exception("优惠券已过期");
            //是否已助力
            if (list != null && list.stream().filter(n -> n.getUnionid().equals(assist.getUnionid())).count() > 0) {
                throw new Exception("不可重复助力");
            }
            QkjprmPromotionAssistrecordEntity entity = new QkjprmPromotionAssistrecordEntity();
            entity.setActiontype(1);
            entity.setAssistedunionid(receiveEntity.getUnionid());
            entity.setHeadimg(assist.getHeadimg());
            entity.setOpenid(assist.getOpenid());
            entity.setUnionid(assist.getUnionid());
            entity.setNickname(assist.getNickname());
            entity.setCreatetime(new Date());
            entity.setIsvalid(1);
            entity.setCouponreceiveid(couponsn);
            //是否已翻倍 是否已助力
            if (list != null && list.stream().filter(n -> n.getIsvalid().equals(1)).count() >= couponEntity.getAssistcount()) {
                entity.setIsvalid(0);
            }
            if (!this.save(entity)) {
                throw new Exception("助力失败");
            }
            queryParam.put("isvalid", 1);
            list = queryAll(queryParam);
            //是否翻倍
            if (list.size() >= couponEntity.getAssistcount()) {
                receiveEntity.setIsdouble(1);
                if (!receiveService.update(receiveEntity)) {
                    throw new Exception("更新翻倍信息失败");
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    /**
     * 助力积分榜得积分
     *
     * @param assist  助力人
     * @param unionid 被助力人unionid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean assistIntegralRank(ReceiveParam assist, String unionid) throws Exception {
        try {
            //被助力人是否存在
            QkjprmPromotionMemberEntity memberEntity = memberService.getMemberByUnionid(unionid);
            if (memberEntity == null) {
                throw new Exception("用户不存在");
            }
            QkjprmPromotionAssistrecordEntity entity = new QkjprmPromotionAssistrecordEntity();
            entity.setActiontype(0);
            entity.setIsvalid(1);
            entity.setOpenid(assist.getOpenid());
            entity.setUnionid(assist.getUnionid());
            entity.setNickname(assist.getNickname());
            entity.setHeadimg(assist.getHeadimg());
            entity.setCreatetime(new Date());
            entity.setAssistedunionid(unionid);
            //是否已助力  本日是否到达上线
            HashMap<String, Object> queryParam = new HashMap<>();
            queryParam.put("assistedunionid", unionid);
            queryParam.put("assistdate", new Date());
            List<QkjprmPromotionAssistrecordEntity> list = queryAll(queryParam);
            if (list != null && (list.size() >= 5 || list.stream().filter(n -> n.getUnionid().equals(assist.getUnionid())).count() > 0)) {
                entity.setIsvalid(0);
            }
            if (!this.save(entity)) {
                throw new Exception("助力失败");
            }
            //更新用户积分
            if (entity.getIsvalid().equals(1)) {
                memberEntity.setAssistintegral(memberEntity.getAssistintegral() + 10);
                if (!memberService.update(memberEntity)) {
                    throw new Exception("助力失败");
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public List<QkjprmPromotionAssistrecordEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjprmPromotionAssistrecordEntity> page = new Query<QkjprmPromotionAssistrecordEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjprmPromotionAssistrecordPage(page, params));
    }

    @Override
    public boolean add(QkjprmPromotionAssistrecordEntity qkjprmPromotionAssistrecord) {
        return this.save(qkjprmPromotionAssistrecord);
    }

    @Override
    public boolean update(QkjprmPromotionAssistrecordEntity qkjprmPromotionAssistrecord) {
        return this.updateById(qkjprmPromotionAssistrecord);
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
