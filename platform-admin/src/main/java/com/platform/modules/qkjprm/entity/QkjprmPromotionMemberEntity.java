/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionMemberEntity.java
 * 包名称:com.platform.modules.qkjprm.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
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
 * @date 2021-12-09 10:46:48
 */
@Data
@TableName("QKJPRM_PROMOTION_MEMBER")
public class QkjprmPromotionMemberEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 产品累计积分
     */
    private Integer productintegral;
    /**
     * 助力累计积分
     */
    private Integer assistintegral;

    /**
     * 累计总积分
     */
    @TableField(exist = false)
    private Integer totalinteger;
    /**
     * 累计优惠券兑换金额
     */
    private BigDecimal exchangeamount;
    /**
     * 性别
     */
    private  Integer sex;
    /**
     * OpenId
     */
    private String openid;
    /**
     * HeadImg
     */
    private String headimg;
    /**
     * UnionId
     */
    private String unionid;
    /**
     * NickName
     */
    private String nickname;
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
