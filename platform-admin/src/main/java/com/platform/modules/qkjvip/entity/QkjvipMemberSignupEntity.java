/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberSignupEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-30 09:20:27        孙珊珊     初版做成
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
 * @date 2020-09-30 09:20:27
 */
@Data
@TableName("QKJVIP_MEMBER_SIGNUP")
public class QkjvipMemberSignupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 预约日期
     */
    private String sdate;
    /**
     * 预约场次
     */
    private String snumber;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 活动id
     */
    private String acitvityId;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 地址
     */
    private String address;

    private Date addtime;
    /**
     * id
     */
    @TableId
    private String id;

    private String memberid;

    @TableField(exist = false)
    private String memadduser;
    @TableField(exist = false)
    private String memadddept;
    @TableField(exist = false)
    private String addressstr;
    @TableField(exist = false)
    private String openid;
    @TableField(exist = false)
    private String oldphone;
    @TableField(exist = false)
    private Integer oldsex;
    @TableField(exist = false)
    private String oldname;
    @TableField(exist = false)
    private Integer integral;
}
