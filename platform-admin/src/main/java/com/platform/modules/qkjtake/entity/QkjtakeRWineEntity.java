/*
 * 项目名称:platform-plus
 * 类名称:QkjtakeRWineEntity.java
 * 包名称:com.platform.modules.qkjtake.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-02-21 14:18:34             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjtake.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 
 * @date 2022-02-21 14:18:34
 */
@Data
@TableName("QKJTAKE_R_WINE")
public class QkjtakeRWineEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 0提交1处理中2已完成-1已取消
     */
    private Integer state;
    /**
     * 会员
     */
    private String memberid;

    private String membername;
    /**
     * 订单id
     */
    private String orderid;
    /**
     * 操作人后台
     */
    private String operator;
    /**
     * 后台操作时间
     */
    private Date operationtime;
    /**
     * takeDate
     */
    private Date takedate;
    /**
     * ReceiveProvince
     */
    private String receiveprovince;
    /**
     * ReceiveAddress
     */
    private String receiveaddress;
    /**
     * receiver
     */
    private String receiver;
    /**
     * ReceiveCity
     */
    private String receivecity;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * ReceiveCounty
     */
    private String receivecounty;
    /**
     * receivephone
     */
    private String receivephone;

    private Integer ordertype;// 0销售订单 1赠送订单

    private Integer taketype; // 0邮寄 1自提

    private String wineid;
    @TableField(exist = false)
    private Integer isout; // 是否已出库
    @TableField(exist = false)
    private String proname;
    @TableField(exist = false)
    private String housename;
    @TableField(exist = false)
    private String proid;
    @TableField(exist = false)
    private String wareid;
    @TableField(exist = false)
    private String wareproid;
    @TableField(exist = false)
    private String wdata;
}
