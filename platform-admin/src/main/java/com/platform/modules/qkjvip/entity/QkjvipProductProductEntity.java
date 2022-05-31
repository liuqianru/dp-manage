/*
 * 项目名称:platform-plus
 * 类名称:QkjvipProductProductEntity.java
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
@TableName("QKJVIP_PRODUCT_PRODUCT")
public class QkjvipProductProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
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
     * SaleUnit
     */
    private Integer saleunit;
    /**
     * PackSize
     */
    private Float packsize;
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


    private String productid;

    private String productimg;

    private BigDecimal quantity;
    @TableField(exist = false)
    private List<QkjvipProductStockEntity> liststock;
    @TableField(exist = false)
    private BigDecimal realtotalprice;
    @TableField(exist = false)
    private Double outcount;
}
