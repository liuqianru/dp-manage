/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrderthpEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-01-13 09:27:21        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-01-13 09:27:21
 */
@Data
@TableName("QKJVIP_MEMBER_ORDERTHP")
public class QkjvipMemberOrderthpEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * OrderId
     */
    private String orderid;
    /**
     * ShopId
     */
    private String shopid;
    /**
     * ShopName
     */
    private String shopname;
    /**
     * ProductId
     */
    private String productid;
    /**
     * ProductName
     */
    private String productname;
    /**
     * SkuCode
     */
    private String skucode;
    /**
     * refundDate
     */
    private String refunddate;
    /**
     * refundMoney
     */
    private Double refundmoney;
    /**
     * SellerAuditStatus
     */
    private String sellerauditstatus;
    /**
     * ManagerConfirmStatus
     */
    private String managerconfirmstatus;
    /**
     * RefundPayStatus
     */
    private String refundpaystatus;
    /**
     * refundStatus
     */
    private String refundstatus;

    private Double returnquantity;

    private String managerconfirmdate;
}
