/*
 * 项目名称:platform-plus
 * 类名称:QkjrptMemberTargetEntity.java
 * 包名称:com.platform.modules.qkjrpt.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-28 13:59:12        liuqianru     初版做成
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
 * @date 2021-09-28 13:59:12
 */
@Data
@TableName("QKJRPT_MEMBER_TARGET")
public class QkjrptMemberTargetEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * 目标年度
     */
    private String targetyear;
    /**
     * 目标人数
     */
    private Integer targetnum;
    /**
     * 创建时间
     */
    private Date createon;
    /**
     * AreaName
     */
    private String areaname;
    /**
     * IdentityLevel
     */
    private String identitylevel;
    /**
     * GroupOrgNo
     */
    private String grouporgno;
    /**
     * MemberGroup
     */
    private String membergroup;
    /**
     * AreaCode
     */
    private String areacode;
    /**
     * IdentityLevelName
     */
    private String identitylevelname;
    /**
     * MemberGroupName
     */
    private String membergroupname;
    /**
     * GroupOrgName
     */
    private String grouporgname;
    /**
     * 单位性质
     */
    private String identitygroup;
    private String identitygroupname;
}
