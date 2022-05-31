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
public class QkjvipMemberActivitymbsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * member_id
     */
    private String memberId;
    /**
     * activity_id
     */
    private String activityId;


    private Integer status;

    private String jobTitle;

    private String memberjson;// 会员json

    @TableField(exist = false)
    private String address;


    /**
     * 会员名称
     */
    @TableField(exist = false)
    @Excel(name = "会员名称", orderNum = "4", width = 15,  fixedIndex = 4)
    private String memberName;
    /*
     * 会员真实姓名
     */
    @TableField(exist = false)
    @Excel(name = "会员真实姓名", orderNum = "5", width = 15,  fixedIndex = 5)
    private String realName;
    /*
     * 会员手机
     */
    @TableField(exist = false)
    @Excel(name = "会员手机号", orderNum = "6", width = 15,  fixedIndex = 6)
    private String mobile;

    @TableField(exist = false)
    private Integer bmstatus;
    @TableField(exist = false)
    private Integer qdstatus;
    @TableField(exist = false)
    private String bmid;
    @TableField(exist = false)
    private String headImgUrl;
    @TableField(exist = false)
    private String servicename;
    @TableField(exist = false)
    private String singtype;
    @TableField(exist = false)
    private Integer smemtype;
    @TableField(exist = false)
    private String memberidto;
    @TableField(exist = false)
    private Integer islayer;
    @TableField(exist = false)
    private String atype;
    @TableField(exist = false)
    private String companyName;
    @TableField(exist = false)
    private String membergroup;
    @TableField(exist = false)
    private Integer identitylevel;
    @TableField(exist = false)
    private String orgUserid;
    @TableField(exist = false)
    private String referrer;


    /**
     * 隐藏列，必须有，否则groupName会报错
     */
    @Excel(name = "隐藏列", orderNum = "0", isColumnHidden = true, fixedIndex = 0)
    @TableField(exist = false)
    private String memberHidden;

    @TableField(exist = false)
    @Excel(name = "活动标题", orderNum = "1", width = 15,  fixedIndex = 1)
    private String title;
    @TableField(exist = false)
    @Excel(name = "活动开始时间", orderNum = "2", width = 15,  fixedIndex = 2)
    private String starDate;
    @TableField(exist = false)
    @Excel(name = "活动结束时间", orderNum = "3", width = 15,  fixedIndex = 3)
    private String endDate;
    @Excel(name = "邀约方式", orderNum = "7", width = 15,  fixedIndex = 7)
    private String statusstr;
    @Excel(name = "报名状态", orderNum = "8", width = 15,  fixedIndex = 8)
    private String bmstatusstr;
    @Excel(name = "签到状态", orderNum = "9", width = 15,  fixedIndex = 9)
    private String qdstatusstr;
}
