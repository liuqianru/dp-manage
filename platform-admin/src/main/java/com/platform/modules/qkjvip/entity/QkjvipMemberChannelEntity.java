/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberChannelEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-09 10:55:27        liuqianru     初版做成
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
 * @date 2020-12-09 10:55:27
 */
@Data
@TableName("QKJVIP_MEMBER_CHANNEL")
public class QkjvipMemberChannelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * MemberChannel
     */
    private Integer memberchannel;
    /**
     * ServiceName
     */
    private String servicename;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * Remark
     */
    private String remark;
    /**
     * CreateOn
     */
    private Date createon;
    /**
     * appid
     */
    private String appid;
    /**
     * 渠道类型（0：线下 1：线上）
     */
    private Integer channeltype;
    /**
     * IsMain
     */
    private Integer ismain;
}
