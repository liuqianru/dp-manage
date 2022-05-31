/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivitymaterialEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-08 13:43:57        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-09-08 13:43:57
 */
@Data
@TableName("QKJVIP_MEMBER_ACTIVITYMATERIAL")
public class QkjvipMemberActivitymaterialEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * materialid
     */
    private String materialid;
    /**
     * mainid
     */
    private String activityId;
    /**
     * add_time
     */
    private Date addTime;

    private String name;

    private Double number;

    private String unit;

    private Double unitprice;

    private String content;

    private Double totalprice;

    private Integer matertype;

    private String memberid;

    private Integer type;

    private Integer recestate;

    private String receremark;

    private String address;

    @TableField(exist = false)
    private String title;
    @TableField(exist = false)
    private String starDate;
    @TableField(exist = false)
    private String endDate;
    @TableField(exist = false)
    private String membername;
    @TableField(exist = false)
    private String realname;
    @TableField(exist = false)
    private String mobile;
    @TableField(exist = false)
    private String companyName;
    @TableField(exist = false)
    private String jobTitle;

}
