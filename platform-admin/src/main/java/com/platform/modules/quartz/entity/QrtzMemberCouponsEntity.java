/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberCouponsEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-17 11:37:32        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-03-17 11:37:32
 */
@Data
@TableName("QKJVIP_MEMBER_COUPONS")
public class QrtzMemberCouponsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * 优惠券Id
     */
    private Long couponid;
    /**
     * 用户id
     */
    private Long userid;
    /**
     * 面值
     */
    private String price;
    /**
     * 面值类型 1折扣2特价3抵现
     */
    private String pricetype;
    /**
     * 使用状态 已使用/未使用/过期
     */
    private String usestatus;
    /**
     * 优惠券创建时间
     */
    private String createtime;
    /**
     * 优惠券使用时间
     */
    private String usedtime;
    /**
     * 优惠券领取时间
     */
    private String receivetime;
    /**
     * 店铺名
     */
    private String shopname;
    /**
     * 生效时间
     */
    private String effectstarttime;
    /**
     * 失效时间
     */
    private String effectendtime;
    /**
     * 使用限制 0 无限制 达到金额）
     */
    private String orderamount;
    /**
     * 是否跨店
     */
    private String isstrideover;
    /**
     * 优惠券限制0全店1品类2品牌3商品
     */
    private String applyscene;
    /**
     * 限制内容
     */
    private String limitname;

    @Override
    public boolean equals(Object o) {
        return o instanceof QrtzMemberCouponsEntity
                && this.couponid != null && this.couponid.equals(((QrtzMemberCouponsEntity)o).couponid)
                && this.userid != null && this.userid.equals(((QrtzMemberCouponsEntity)o).userid);
    }
}
