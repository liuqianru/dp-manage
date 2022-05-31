/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderOrderdetailEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-10 11:37:08        孙珊珊     初版做成
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
 * @date 2021-03-10 11:37:08
 */
@Data
@TableName("QKJVIP_ORDER_ORDERDETAIL")
public class QkjvipOrderOrderdetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * MOrderId
     */
    private String morderid;
    /**
     * ProductId
     */
    private String productid;
    /**
     * ProductName
     */
    private String productname;
    /**
     * ProductType
     */
    private Integer producttype;
    /**
     * SalePrice
     */
    private BigDecimal saleprice;
    /**
     * Quantity
     */
    private BigDecimal quantity;
    /**
     * RealTotalPrice
     */
    private BigDecimal realtotalprice;
    /**
     * SaleUnit
     */
    private Integer saleunit;

    @TableField(exist = false)
    private String saleunitname;
    /**
     * PackSize
     */
    private Float packsize;
    /**
     * IsGift
     */
    private Integer isgift;
    /**
     * SkuCode
     */
    private String skucode;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * CreateTime
     */
    private Date createtime;
    /**
     * Remark
     */
    private String remark;
    /**
     * BottleTypeId
     */
    private String bottletypeid;
    /**
     * BottleTypeName
     */
    private String bottletypename;
    private BigDecimal totalbottlenum;
    @TableField(exist = false)
    private List<QkjvipProductStockEntity> liststock;
    @TableField(exist = false)
    private Double outcount;
    @TableField(exist = false)
    private String membername;
    @TableField(exist = false)
    private String housename;
    @TableField(exist = false)
    private String warehouseid;
}
