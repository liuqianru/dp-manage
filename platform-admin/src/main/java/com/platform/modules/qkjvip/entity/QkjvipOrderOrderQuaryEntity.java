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
public class QkjvipOrderOrderQuaryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String  productname;
    private String  ordercode;
    private Integer  ordertype;
    private Integer  orderstatus;
    private String  startorderdate;
    private String  endorderdate;
    private String  shopname;
    private String  membername;
    private String memberid;
    private String  cellphone;
    private BigDecimal  startorderamount;
    private BigDecimal  endorderamount;
    private Integer  pageindex;
    private Integer  pagesize;
    private String orderid;
    private String morderid;
    private String id;
    private String visitid;
    private String currentmemberid;  // 当前登录用户id
    private String listmemberchannel;  // 用户的渠道权限
    private String listordertype; // 订单类型列列表
    private Integer datetype;
    private String servicename;
    private Boolean isneworder;
    private Boolean isformal;
    private String listorgno;  // 用户的权限部门
}
