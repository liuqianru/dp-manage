/*
 * 项目名称:platform-plus
 * 类名称:QkjvipProductEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-23 15:23:32        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-08-23 15:23:32
 */
@Data
@TableName("QKJVIP_PRODUCT")
public class QkjvipProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 产品名称
     */
    private String productname;
    /**
     * 产品类型（1：酒类 2：物料）
     */
    private Integer producttype;
    /**
     * 单价
     */
    private BigDecimal saleprice;
    /**
     * 单位
     */
    private String saleunit;
    /**
     * erp编号
     */
    private String skucode;
    /**
     * 是否有效（0：是 1：否）
     */
    private Integer status;
    /**
     * 库存
     */
    private Integer stocksize;
    /**
     * CreateUser
     */
    private String createuser;
    /**
     * CreateTime
     */
    private Date createtime;
    /**
     * Id
     */
    @TableId
    private String id;
}
