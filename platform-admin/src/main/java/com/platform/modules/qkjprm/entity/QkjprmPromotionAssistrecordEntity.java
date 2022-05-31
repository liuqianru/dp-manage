/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionAssistrecordEntity.java
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
import java.util.Date;

/**
 * 实体
 *
 * @author hanjie
 * @date 2021-12-09 10:46:48
 */
@Data
@TableName("QKJPRM_PROMOTION_ASSISTRECORD")
public class QkjprmPromotionAssistrecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 优惠券领取id
     */
    private String couponreceiveid;
    /**
     * 0 访问 1 助力
     */
    private Integer actiontype;
    /**
     * 被助力人unionid
     */
    private String assistedunionid;
    /**
     * 是否有效
     */
    private Integer isvalid;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * HeadImg
     */
    private String headimg;
    /**
     * UnionId
     */
    private String unionid;
    /**
     * CreateTime
     */
    private Date createtime;
    /**
     * NickName
     */
    private String nickname;
    /**
     * OpenId
     */
    private String openid;
}
