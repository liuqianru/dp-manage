/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivityshopEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-22 16:21:27             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author 
 * @date 2022-04-22 16:21:27
 */
@Data
@TableName("QKJVIP_MEMBER_ACTIVITYSHOP")
public class QkjvipMemberActivityshopEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 1系统邀约/2自主报名/3现场报名
     */
    private String pername;
    /**
     * org_name
     */
    private String orgName;
    /**
     * org_id
     */
    private String orgId;
    /**
     * activity_id
     */
    private String activityId;
    /**
     * mobile
     */
    private String mobile;
    /**
     * id
     */
    @TableId
    private String id;
}
