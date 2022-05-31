/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberScancodeNewEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-30 09:35:18        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author hanjie
 * @date 2022-03-30 09:35:18
 */
@Data
@TableName("QKJVIP_MEMBER_SCANCODE_NEW")
public class QkjvipMemberScancodeNewEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Lng
     */
    private String lng;
    /**
     * PhoneNo
     */
    private String phoneno;
    /**
     * ActivityName
     */
    private String activityname;
    /**
     * BarCode
     */
    private String barcode;
    /**
     * Id
     */
    @TableId
    private Long id;
    /**
     * CreateTime
     */
    private Date createtime;
    /**
     * NickName
     */
    private String nickname;
    /**
     * BottleCode
     */
    private String bottlecode;
    /**
     * Address
     */
    private String address;
    /**
     * ActivityType
     */
    private Integer activitytype;
    /**
     * UnionId
     */
    private String unionid;
    /**
     * AwardType
     */
    private Integer awardtype;
    /**
     * RecordId
     */
    private Integer recordid;
    /**
     * MaterialCode
     */
    private String materialcode;
    /**
     * WeixinAppId
     */
    private String weixinappid;
    /**
     * ProductName
     */
    private String productname;
    /**
     * Province
     */
    private String province;
    /**
     * Lat
     */
    private String lat;
    /**
     * Amount
     */
    private String amount;
    /**
     * PrizeName
     */
    private String prizename;
    /**
     * ProductId
     */
    private Integer productid;
    /**
     * ScanTime
     */
    private Date scantime;
    /**
     * OpenId
     */
    private String openid;
    /**
     * City
     */
    private String city;
    /**
     * ShopName
     */
    private String shopname;
}
