/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberRightsConfigEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-30 09:23:01        liuqianru     初版做成
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
 * @date 2021-04-30 09:23:01
 */
@Data
@TableName("QKJVIP_MEMBER_RIGHTS_CONFIG")
public class QkjvipMemberRightsConfigEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * ConfigNo
     */
    private String configno;
    /**
     * MemberLevel
     */
    private Integer memberlevel;
    /**
     * MemberLevelName
     */
    private String memberlevelname;
    /**
     * SendLevel
     */
    private Integer sendlevel;
    /**
     * SendLevelName
     */
    private String sendlevelname;
    /**
     * ValidStartTime
     */
    private Date validstarttime;
    /**
     * ValidEndTime
     */
    private Date validendtime;
    /**
     * SendCount
     */
    private Integer sendcount;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * CreateOn
     */
    private Date createon;

    /**
     * Remark
     */
    private String remark;

    /**
     * Creator
     */
    private String creator;
}
