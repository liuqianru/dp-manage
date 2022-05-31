/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivitymbsEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-18 13:35:24        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2020-09-18 13:35:24
 */
@Data
@TableName("QKJVIP_MEMBER_ACTIVITYMBS")
public class QkjvipMemberActivityGiftEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;

    private String memberjson;// 会员json

    private Integer status;

    @TableField(exist = false)
    @Excel(name = "活动标题", orderNum = "1", width = 15)
    private String title;

    @Excel(name = "单位名称", orderNum = "2", width = 15)
    private String companyName;

    /**
     * 会员名称
     */
    @TableField(exist = false)
    @Excel(name = "会员名称", orderNum = "3", width = 15)
    private String memberName;
    /*
     * 会员真实姓名
     */
    @TableField(exist = false)
    private String realName;
    /*
     * 会员手机
     */
    @TableField(exist = false)
    @Excel(name = "会员手机号", orderNum = "4", width = 15)
    private String mobile;


    @Excel(name = "职务", orderNum = "5", width = 15)
    private String jobTitle;


    /**
     * 省
     */
    @Excel(name = "收货地址-省", orderNum = "6", width = 15)
    private String province;
    /**
     * 用户所在城市
     */
    @Excel(name = "收货地址-市", orderNum = "7", width = 15)
    private String city;
    /**
     * 地区
     */
    @Excel(name = "收货地址-区县", orderNum = "8", width = 15)
    private String district;

    @TableField(exist = false)
    @Excel(name = "收货地址", orderNum = "9", width = 15)
    private String address;

    @TableField(exist = false)
    @Excel(name = "身份类型", orderNum = "10", width = 15)
    private String identitygroupname;

    @TableField(exist = false)
    @Excel(name = "身份等级", orderNum = "11", width = 15)
    private String identitylevelname;

    @TableField(exist = false)
    @Excel(name = "区域", orderNum = "12", width = 15)
    private String area;

    @TableField(exist = false)
    @Excel(name = "介绍人", orderNum = "13", width = 15)
    private String referrerName;

    @TableField(exist = false)
    @Excel(name = "介绍人手机", orderNum = "14", width = 15)
    private String referrermobile;

    @TableField(exist = false)
    @Excel(name = "获客渠道", orderNum = "15", width = 15)
    private String grouporgname;

    @TableField(exist = false)
    @Excel(name = "维护人", orderNum = "16", width = 15)
    private String orgusername;

    @TableField(exist = false)
    @Excel(name = "维护人手机", orderNum = "17", width = 15)
    private String orgusermobile;

    @TableField(exist = false)
    @Excel(name = "核心店/团购经销商", orderNum = "18", width = 15)
    private String orgname;

    @TableField(exist = false)
    @Excel(name = "性别", orderNum = "19", width = 15)
    private String sex;

    @TableField(exist = false)
    @Excel(name = "民族", orderNum = "20", width = 15)
    private String nationname;

    @TableField(exist = false)
    @Excel(name = "接收状态", orderNum = "21", width = 15,replace={"已接收_1","拒绝_2","未知_0"})
    private Integer recestate;

    @TableField(exist = false)
    @Excel(name = "拒绝原因", orderNum = "22", width = 20)
    private String receremark;



}
