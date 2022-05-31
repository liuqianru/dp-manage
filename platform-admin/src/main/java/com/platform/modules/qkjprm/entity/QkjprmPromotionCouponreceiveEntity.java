/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponreceiveEntity.java
 * 包名称:com.platform.modules.qkjprm.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:49        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体
 *
 * @author hanjie
 * @date 2021-12-09 10:46:49
 */
@Data
@TableName("QKJPRM_PROMOTION_COUPONRECEIVE")
public class QkjprmPromotionCouponreceiveEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 优惠券列表
     */
    private String couponid;
    /**
     * 优惠券金额
     */
    private BigDecimal couponamount;
    /**
     * 领取人unionid
     */
    private String unionid;
    /**
     * 领取人openid
     */
    private String openid;
    /**
     * 领取人昵称
     */
    private String nickname;
    /**
     * 领取人头像
     */
    private String headimg;
    /**
     * 领取人性别
     */
    private Integer sex;
    /**
     * 需助力次数
     */
    private Integer assistcount;
    /**
     * 是否翻倍
     */
    private Integer isdouble;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * CreateTime
     */
    private Date createtime;
}
