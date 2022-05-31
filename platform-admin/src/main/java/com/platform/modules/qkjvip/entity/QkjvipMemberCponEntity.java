/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberCponEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-17 14:46:26        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2020-12-17 14:46:26
 */
@Data
@TableName("QKJVIP_MEMBER_CPON")
public class QkjvipMemberCponEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * title
     */
    private String title;

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
    private String addTime;

    private String cponid;

    @TableField(exist = false)
    private List<QkjvipMemberCponsonEntity> sonlists;
    @TableField(exist = false)
    private List<QkjvipMemberCponEntity> ms;
    @TableField(exist = false)
    private Double totalnum;
    @TableField(exist = false)
    private Double usenum;

    /**
     * 优惠券
     */
    @TableField(exist = false)
    private Integer CouponTypeId;
    @TableField(exist = false)
    private Integer StoreId;
    @TableField(exist = false)
    private String StoreName;
    @TableField(exist = false)
    private Double Discount;
    @TableField(exist = false)
    private Double ReduceMoney;
    @TableField(exist = false)
    private Double LimitOrderAmount;
    @TableField(exist = false)
    private String LimitProduct;
    @TableField(exist = false)
    private String UseEndTime;
    @TableField(exist = false)
    private Integer SendNum;
    @TableField(exist = false)
    private String Desc;
    @TableField(exist = false)
    private String UserLevelLower;
    @TableField(exist = false)
    private Integer[] AllowLevel;
}
