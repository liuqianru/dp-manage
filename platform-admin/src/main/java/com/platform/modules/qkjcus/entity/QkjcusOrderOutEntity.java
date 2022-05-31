/*
 * 项目名称:platform-plus
 * 类名称:QkjcusOrderOutEntity.java
 * 包名称:com.platform.modules.qkjcus.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-28 10:14:18             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareproEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 
 * @date 2021-12-28 10:14:18
 */
@Data
@TableName("QKJCUS_ORDER_OUT")
public class QkjcusOrderOutEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 入库id
     */
    private String instockid;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * remark
     */
    private String remark;

    private String enclosure;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * add_user
     */
    private String addUser;
    /**
     * totleprice
     */
    private Double totleprice;
    /**
     * OrderCode
     */
    private String ordercode;
    /**
     * member_name
     */
    private String memberName;
    /**
     * wareproid
     */
    private String wareproid;

    @TableField(exist = false)
    private QkjvipOrderWareproEntity stockmsg;

    @TableField(exist = false)
    private String wineid;
    @TableField(exist = false)
    private String wdata;
}
