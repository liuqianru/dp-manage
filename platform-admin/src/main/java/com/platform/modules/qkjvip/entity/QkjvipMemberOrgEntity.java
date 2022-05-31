/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrgEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-24 09:57:29        liuqianru     初版做成
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
 * @date 2021-09-24 09:57:29
 */
@Data
@TableName("QKJVIP_MEMBER_ORG")
public class QkjvipMemberOrgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 机构编码
     */
    @TableId
    private String orgNo;
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 状态  0：无效   1：有效
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建者ID
     */
    private String createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
}
