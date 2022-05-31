/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponexchangeEntity.java
 * 包名称:com.platform.modules.qkjprm.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
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
 * @date 2021-12-09 10:46:48
 */
@Data
@TableName("QKJPRM_PROMOTION_COUPONEXCHANGE")
public class QkjprmPromotionCouponexchangeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 领取记录id
     */
    private String couponreceiveid;
    /**
     * 营销码
     */
    private String marketcode;
    /**
     * 兑现金额
     */
    private BigDecimal exchangeamount;
    /**
     * 是否翻倍
     */
    private Integer isdouble;
    /**
     * 兑现转账流水号
     */
    private String exchangesn;
    /**
     * 兑换结果
     */
    private Integer exchangeresult;
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
     * UserId
     */
    private String userid;
    /**
     * UserUnionId
     */
    private String userunionid;
}
