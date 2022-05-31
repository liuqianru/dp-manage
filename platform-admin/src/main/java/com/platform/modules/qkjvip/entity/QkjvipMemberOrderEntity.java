/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrderEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-11 14:15:23        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2020-08-11 14:15:23
 */
@Data
@TableName("QKJVIP_MEMBER_ORDER")
public class QkjvipMemberOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private String id;
    /**
     * 
     */
    @Excel(name = "订单编号", orderNum = "0", width = 15)
    private String orderid;
    /**
     * 
     */
    @Excel(name = "商品名称", orderNum = "1", width = 15)
    private String productname;
    /**
     * 
     */
    @Excel(name = "单价", orderNum = "2", width = 15)
    private String saleprice;
    /**
     * 
     */
    @Excel(name = "数量", orderNum = "3", width = 15)
    private String quantity;
    /**
     * 
     */
    @Excel(name = "金额", orderNum = "4", width = 15)
    private String realtotalprice;
    /**
     * 
     */
    @Excel(name = "订单日期", orderNum = "5", width = 15)
    private String orderdate;
    /**
     * 
     */
    @Excel(name = "买家手机", orderNum = "6", width = 15)
    private String cellphone;
    /**
     * 
     */
    @Excel(name = "买家类型", orderNum = "7", width = 15)
    private String buyertype;
    /**
     * 
     */
    @Excel(name = "所属店铺", orderNum = "8", width = 15)
    private String shopname;
    /**
     * 
     */
    @Excel(name = "所属店铺省", orderNum = "9", width = 15)
    private String shopprovincename;
    /**
     * 
     */
    @Excel(name = "所属店铺市", orderNum = "10", width = 15)
    private String shopcityname;
    /**
     * 
     */
    @Excel(name = "订单状态", orderNum = "11", width = 15)
    private String orderstatus;
    /**
     * 
     */
    @Excel(name = "是否是赠品", orderNum = "12", width = 15,replace={"非_0","是_1"})
    private Integer giveaway;

    private Integer source;

    private String skucode;

    private String paydate;
    private String finishdate;
    private String paymenttypename;
    private String shopid;
    private String productid;
    private String orderchannel;

}
