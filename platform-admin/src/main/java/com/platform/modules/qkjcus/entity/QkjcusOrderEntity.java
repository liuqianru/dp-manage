/*
 * 项目名称:platform-plus
 * 类名称:QkjcusOrderEntity.java
 * 包名称:com.platform.modules.qkjcus.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-23 16:50:14             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareproEntity;
import com.platform.modules.qkjwine.entity.QkjwineRInfoEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author 
 * @date 2021-12-23 16:50:14
 */
@Data
@TableName("QKJCUS_ORDER")
public class QkjcusOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * totleprice
     */
    private Double totleprice;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * per_change
     */
    private String perChange;
    /**
     * ReceiveProvince
     */
    private String receiveprovince;
    /**
     * ReceiveCity
     */
    private String receivecity;
    /**
     * member_name
     */
    private String memberName;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * ReceiveAddress
     */
    private String receiveaddress;
    /**
     * mobile
     */
    private String mobile;
    /**
     * member_id
     */
    private String memberId;
    /**
     * ReceiveCounty
     */
    private String receivecounty;
    /**
     * add_user
     */
    private String addUser;

    private String receivephone;

    private String receiver;

    private String ordercode;

    private String remark;

    private String enclosure;

    private Date custime;

    @TableField(exist = false)
    private Integer issms; // 是否已发短信 1 已发
    @TableField(exist = false)
    private List<QkjcusOrderProEntity> pros;
    @TableField(exist = false)
    private List<QkjcusOrderOutEntity> outs;
    @TableField(exist = false)
    private QkjvipOrderWareproEntity stockmsg;
    @TableField(exist = false)
    private List<QkjwineRInfoEntity> wines;
}
