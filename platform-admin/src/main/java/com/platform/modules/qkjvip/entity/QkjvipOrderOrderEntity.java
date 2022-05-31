/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderOrderEntity.java
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
import com.platform.modules.qkjwine.entity.QkjwineRInfoEntity;
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
@TableName("QKJVIP_ORDER_ORDER")
public class QkjvipOrderOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**H
     * Id
     */
    @TableId
    private String id;
    /**
     * OrderDate
     */
    private Date orderdate;
    /**
     * OrderStatus
     */
    private Integer orderstatus;
    /**
     * PayStatus
     */
    private Integer paystatus;
    /**
     * DeliveryStatus
     */
    private Integer deliverystatus;
    /**
     * PayAmount
     */
    private BigDecimal payamount;
    /**
     * CellPhone
     */
    private String cellphone;
    /**
     * BuyerType
     */
    private String buyertype;
    /**
     * ShopName
     */
    private String shopname;
    /**
     * ShopProvinceName
     */
    private String shopprovincename;
    /**
     * ShopCityName
     */
    private String shopcityname;
    /**
     * PayTime
     */
    private String paytime;
    /**
     * CompletedTime
     */
    private Date completedtime;
    /**
     * PaymentTypeName
     */
    private String paymenttypename;
    /**
     * OrderAmount
     */
    private BigDecimal orderamount;
    /**
     * DiscountAmount
     */
    private BigDecimal discountamount;
    /**
     * OrderType
     */
    private Integer ordertype;
    /**
     * ReceiveProvince
     */
    private String receiveprovince;
    /**
     * ReceiveCity
     */
    private String receivecity;
    /**
     * ReceiveCounty
     */
    private String receivecounty;
    /**
     * ReceiveAddress
     */
    private String receiveaddress;
    /**
     * Receiver
     */
    private String receiver;
    /**
     * ReceivePhone
     */
    private String receivephone;
    /**
     * OwnedUserId
     */
    private String owneduserid;
    /**
     * OwnedUserName
     */
    private String ownedusername;
    /**
     * CreateTime
     */
    private Date createtime;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * Remark
     */
    private String remark;
    /**
     * UserRemark
     */
    private String userremark;
    /**
     * ERPOrderCode
     */
    private String erpordercode;
    /**
     * JueRuOrderId
     */
    private String jueruorderid;
    /**
     * CreatorAdmin
     */
    private String creatoradmin;
    /**
     * crm_memberid
     */
    private String crmMemberid;
    /**
     * CreatorAdminId
     */
    private String creatoradminid;
    /**
     * UpdateByName
     */
    private String updatebyname;
    /**
     * UpdateTime
     */
    private Date updatetime;
    /**
     * MemberName
     */
    private String membername;
    /**
     * OrderCode
     */
    private String ordercode;
    /**
     * 拜访id
     */
    private String visitid;

    private String payusername;

    private String erporderid;

    private String afexpiretime;

    private Double annualfee;

    @TableField(exist = false)
    private Integer issms; // 是否已发短信 1 已发

    @TableField(exist = false)
    private List<QkjvipProductProductEntity> listproduct;
    @TableField(exist = false)
    private List<QkjvipOrderOrderfileEntity> listfile;
    @TableField(exist = false)
    private List<QkjvipProductProductEntity> listorderproduct;
    @TableField(exist = false)
    private List<QkjvipProductStockEntity> liststock;
    @TableField(exist = false)
    private List<QkjvipOrderDeliverlogEntity> listout;
    @TableField(exist = false)
    private String orderid;
    @TableField(exist = false)
    private String morderid;
    @TableField(exist = false)
    private List<QkjvipProductStockEntity> liststocksumout;
    @TableField(exist = false)
    private String memberid;
    @TableField(exist = false)
    private List<QkjvipOrderOrderfileEntity> listuserfile;
    @TableField(exist = false)
    private List<QkjvipOrderOrderfileEntity> listfinalfile;
    @TableField(exist = false)
    private List<QkjwineRInfoEntity> wines;
    @TableField(exist = false)
    private  Integer toqkh;
    @TableField(exist = false)
    private Boolean isqkhorder;
    @TableField(exist = false)
    private String productid;

    @TableField(exist = false)
    private String wineid;
    @TableField(exist = false)
    private String wdata;

}
