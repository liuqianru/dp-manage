/*
 * 项目名称:platform-plus
 * 类名称:QkjcusOrderProEntity.java
 * 包名称:com.platform.modules.qkjcus.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-23 16:50:14             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 
 * @date 2021-12-23 16:50:14
 */
@Data
@TableName("QKJCUS_ORDER_PRO")
public class QkjcusOrderProEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * price
     */
    private Double price;
    /**
     * wareproid
     */
    private String wareproid;

    private String mainid; // 主表id

    private Integer num;

    @TableField(exist = false)
    private Double totleprice;
    @TableField(exist = false)
    private String memberName;
    @TableField(exist = false)
    private String receivephone;
    @TableField(exist = false)
    private String ordercode;
    @TableField(exist = false)
    private String mobile;
    @TableField(exist = false)
    private Date addTime;
    @TableField(exist = false)
    private String addUser;
    @TableField(exist = false)
    private String housename;
    @TableField(exist = false)
    private String proname;
    @TableField(exist = false)
    private String perChange;
    @TableField(exist = false)
    private String proid;
    @TableField(exist = false)
    private String wareid;
    /**
     * ReceiveProvince
     */
    @TableField(exist = false)
    private String receiveprovince;
    /**
     * ReceiveCity
     */
    @TableField(exist = false)
    private String receivecity;
    /**
     * ReceiveAddress
     */
    @TableField(exist = false)
    private String receiveaddress;
    /**
     * member_id
     */
    @TableField(exist = false)
    private String memberId;
    /**
     * ReceiveCounty
     */
    @TableField(exist = false)
    private String receivecounty;
    @TableField(exist = false)
    private String receiver;
    @TableField(exist = false)
    private String remark;
    @TableField(exist = false)
    private String instockid;


}
