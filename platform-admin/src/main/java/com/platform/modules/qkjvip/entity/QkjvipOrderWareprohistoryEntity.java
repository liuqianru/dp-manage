/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderWareprohistoryEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-22 13:59:47        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-12-22 13:59:47
 */
@Data
@TableName("QKJVIP_ORDER_WAREPROHISTORY")
public class QkjvipOrderWareprohistoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 0 未封坛 1未认购 2已认购 3已借用
     */
    private Integer state;
    /**
     * CreateOn
     */
    private Date createon;
    /**
     * Remark
     */
    private String remark;
    /**
     * num
     */
    private Integer num;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * wareid
     */
    private String wareid;
    /**
     * Creator
     */
    private String creator;

    @TableField(exist = false)
    private Date updatetime;
    @TableField(exist = false)
    private Double annualfee;
    @TableField(exist = false)
    private Date afexpiretime;
    @TableField(exist = false)
    private String membername;
    @TableField(exist = false)
    private String mobile;

    private String proid;

    private String proname;

    private String orderid;
    @TableField(exist = false)
    private String remarkname;
}
