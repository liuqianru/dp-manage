/*
 * 项目名称:platform-plus
 * 类名称:QkjvipProductStockEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-15 15:49:03        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-03-15 15:49:03
 */
@Data
@TableName("QKJVIP_PRODUCT_STOCK")
public class QkjvipProductStockEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * WareHouseId
     */
    private String warehouseid;
    /**
     * HouseName
     */
    private String housename;
    /**
     * ProductId
     */
    private String productid;
    /**
     * ProductName
     */
    private String productname;
    /**
     * ProductCount
     */
    private BigDecimal productcount;
    /**
     * WarningCount
     */
    private BigDecimal warningcount;
    /**
     * OutTotalCount
     */
    private BigDecimal outtotalcount;
    /**
     * InTotalCount
     */
    private BigDecimal intotalcount;
    /**
     * Remark
     */
    private String remark;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * Creator
     */
    private String creator;
    /**
     * CreateOn
     */
    private Date createon;
    private String orderid;

    @TableField(exist = false)
    private String memberid;
    @TableField(exist = false)
    private String membername;
    @TableField(exist = false)
    private List<QkjvipProductStockEntity> details;
    @TableField(exist = false)
    private Double annualfee;
    @TableField(exist = false)
    private String afexpiretime;
    private String wareproid;
    @TableField(exist = false)
    private String isorderadd; // 是否是页面订单传过的数据
    @TableField(exist = false)
    private Integer detailnum; // 产品详情数量
}
