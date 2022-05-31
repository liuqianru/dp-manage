/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntentionorderEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-01-14 09:44:56        liuqianru     初版做成
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
 * @date 2021-01-14 09:44:56
 */
@Data
@TableName("QKJVIP_MEMBER_INTENTIONORDER")
public class QkjvipMemberIntentionorderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 单位
     */
    private String unit;
    /**
     * 拜访主表id
     */
    private String visitId;
    /**
     * 总价
     */
    private Double totalprice;
    /**
     * 会员id
     */
    private String memberId;
    /**
     * 备注
     */
    private String content;
    /**
     * 订单状态(0：未完成 1：已完成）
     */
    private Integer status;
    /**
     * add_user
     */
    private String addUser;
    /**
     * add_dept
     */
    private String addDept;
    /**
     * add_time
     */
    private Date addTime;
}
