/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponEntity.java
 * 包名称:com.platform.modules.qkjprm.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:49        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("QKJPRM_PROMOTION_COUPON")
public class QkjprmPromotionCouponEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 优惠券名称
     */
    private String couponname;
    /**
     * 活动开始时间
     */
    private Date actionstartdate;
    /**
     * 活动结束日期
     */
    private Date actionenddate;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 优惠券金额
     */
    private BigDecimal couponamount;
    /**
     * 需助力次数
     */
    private Integer assistcount;
    /**
     * 对应商品id
     */
    private String productid;
    /**
     * 商品名称
     */
    private String productname;

    /**
     * 产品积分数
     */
    private Integer productintegral;
    /**
     * Disabled
     */
    private Integer disabled;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * CreateTime
     */
    private Date createtime;

    /**
     * 是否领取过
     */
    @TableField(exist = false)
    private boolean isreceive;

    /**
     * 领取的优惠券券码
     */
    @TableField(exist = false)
    private String couponsn;

    /**
     * 是否使用
     */
    @TableField(exist = false)
    private boolean isexchange;
    /**
     * 领取人unionid
     */
    @TableField(exist = false)
    private String receiveuserunionid;

}
