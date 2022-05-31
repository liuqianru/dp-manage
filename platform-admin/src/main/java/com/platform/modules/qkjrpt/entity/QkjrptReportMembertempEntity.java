/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportMembertempEntity.java
 * 包名称:com.platform.modules.qkjrpt.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 10:51:12        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-09-15 10:51:12
 */
@Data
@TableName("QKJRPT_REPORT_MEMBERTEMP")
public class QkjrptReportMembertempEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;

    /**
     * Birthday
     */
    private Date birthday;
    /**
     * IdentityLevelName
     */
    private String identitylevelname;
    /**
     * CreateOn
     */
    private Date createon;
    /**
     * Address
     */
    private String address;
    /**
     * OrgUserName
     */
    private String orgusername;
    /**
     * MemberLevel
     */
    private Integer memberlevel;
    /**
     * Referrer
     */
    private String referrer;
    /**
     * Province
     */
    private String province;
    /**
     * Hobby
     */
    private String hobby;
    /**
     * Mobile
     */
    private String mobile;
    /**
     * MemberLevelName
     */
    private String memberlevelname;
    /**
     * OppositePerson
     */
    private String oppositeperson;
    /**
     * City
     */
    private String city;
    /**
     * PriceSegment
     */
    private String pricesegment;
    /**
     * AreaCode
     */
    private String areacode;
    /**
     * MemberGroup
     */
    private String membergroup;
    /**
     * OrgUserMobile
     */
    private String orgusermobile;
    /**
     * JobTitle
     */
    private String jobtitle;
    /**
     * AreaName
     */
    private String areaname;
    /**
     * YearWineAmt
     */
    private String yearwineamt;
    /**
     * SuperiorUnit
     */
    private String superiorunit;
    /**
     * IdentityLevel
     */
    private Integer identitylevel;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * DepNo
     */
    private String depno;
    /**
     * CompanyName
     */
    private String companyname;
    /**
     * IsLikeOther
     */
    private Integer islikeother;
    /**
     * DepName
     */
    private String depname;
    /**
     * MemberGroupName
     */
    private String membergroupname;
    /**
     * Sex
     */
    private Integer sex;
    /**
     * BuyPurpose
     */
    private String buypurpose;
    /**
     * MemberName
     */
    private String membername;
    /**
     * District
     */
    private String district;

    @TableField(exist = false)
    private Integer activityCnt;
    @TableField(exist = false)
    private Double activityAmt;
}
