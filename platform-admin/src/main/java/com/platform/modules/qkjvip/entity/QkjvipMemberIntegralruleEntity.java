/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntegralruleEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-01-12 10:05:39        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @date 2021-01-12 10:05:39
 */
@Data
@TableName("QKJVIP_MEMBER_INTEGRALRULE")
public class QkjvipMemberIntegralruleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 规则id
     */
    private String ruleid;
    /**
     * 要发放的积分
     */
    private Integer integral;
    /**
     * 每日上限得分
     */
    @TableField(fill = FieldFill.UPDATE)
    private Integer dailylimit;
    /**
     * 每月上限得分
     */
    @TableField(fill = FieldFill.UPDATE)
    private Integer monthlylimit;
    /**
     * 备注
     */
    private String content;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 添加时间
     */
    private Date addTime;
}
