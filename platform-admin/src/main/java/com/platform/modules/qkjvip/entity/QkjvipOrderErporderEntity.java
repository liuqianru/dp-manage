/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderErporderEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-06-21 09:21:21        孙珊珊     初版做成
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
 * @date 2021-06-21 09:21:21
 */
@Data
@TableName("QKJVIP_ORDER_ERPORDER")
public class QkjvipOrderErporderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * OrderType
     */
    private Integer ordertype;
    /**
     * CreateOn
     */
    private Date createon;
    /**
     * ReceiveAmount
     */
    private BigDecimal receiveamount;
    /**
     * Remark
     */
    private String remark;
    /**
     * MaterialTypeName
     */
    private String materialtypename;
    /**
     * OrgType
     */
    private String orgtype;
    /**
     * PackageFactor
     */
    private String packagefactor;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * ReceiveDiffAmount
     */
    private BigDecimal receivediffamount;
    /**
     * BuyCompanyName
     */
    private String buycompanyname;
    /**
     * SeriesName
     */
    private String seriesname;
    /**
     * ActualTaxTotalAmount
     */
    private BigDecimal actualtaxtotalamount;
    /**
     * DepartmentName
     */
    private String departmentname;
    /**
     * Creator
     */
    private String creator;
    /**
     * ProductCode
     */
    private String productcode;
    /**
     * OrderRemark
     */
    private String orderremark;
    /**
     * MOrderId
     */
    private String morderid;
    /**
     * OrderCode
     */
    private String ordercode;
    /**
     * ProductNum
     */
    private BigDecimal productnum;
    /**
     * OrderDate
     */
    private Date orderdate;
    /**
     * BrandName
     */
    private String brandname;
    /**
     * SaleAmount
     */
    private BigDecimal saleamount;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * CompanyName
     */
    private String companyname;
    /**
     * ProductName
     */
    private String productname;
    /**
     * BoxNum
     */
    private BigDecimal boxnum;
    /**
     * SalesMan
     */
    private String salesman;
    /**
     * RecordId
     */
    private String recordid;
    /**
     * TaxPrice
     */
    private BigDecimal taxprice;
    /**
     * TaxTotalAmount
     */
    private BigDecimal taxtotalamount;

    @TableField(exist = false)
    private List<QkjvipOrderErporderEntity> detaillist;
}
