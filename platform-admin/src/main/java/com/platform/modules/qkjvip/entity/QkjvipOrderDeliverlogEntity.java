/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderDeliverlogEntity.java
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
import com.platform.modules.qkjtake.entity.QkjtakeRWineEntity;
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
@TableName("QKJVIP_ORDER_DELIVERLOG")
public class QkjvipOrderDeliverlogEntity implements Serializable {
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
     * WareHouseName
     */
    private String warehousename;
    /**
     * OrderId
     */
    private String orderid;
    /**
     * OrderCode
     */
    private String ordercode;
    /**
     * DeliverCode
     */
    private String delivercode;
    /**
     * ProductId
     */
    private String productid;
    /**
     * ProductName
     */
    private String productname;
    /**
     * ProductNum
     */
    private BigDecimal productnum;
    /**
     * SaleUnit
     */
    private Integer saleunit;
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
    private String createon;
    private Double synum;
    private Float packsize;
    @TableField(exist = false)
    private String addusername;
    @TableField(exist = false)
    private String membername;

    @TableField(exist = false)
    private List<QkjtakeRWineEntity> takes;
}
