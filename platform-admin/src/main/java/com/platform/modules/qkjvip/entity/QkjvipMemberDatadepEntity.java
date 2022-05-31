/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberDatadepEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 17:33:07        liuqianru     初版做成
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
 * @author liuqianru
 * @date 2021-08-04 17:33:07
 */
@Data
@TableName("QKJVIP_MEMBER_DATADEP")
public class QkjvipMemberDatadepEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * OrgNo
     */
    private String orgno;
    /**
     * IsMain
     */
    private Boolean ismain;
    /**
     * CreateOn
     */
    private Date createon;
    /**
     * MemberId
     */
    private String memberid;
    /**
     * Remark
     */
    private String remark;
    /**
     * OrgName
     */
    private String orgname;
    /**
     * Disabled
     */
    private Boolean disabled;
}
