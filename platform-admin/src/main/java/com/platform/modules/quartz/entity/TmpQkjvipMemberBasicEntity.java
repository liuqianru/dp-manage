/*
 * 项目名称:platform-plus
 * 类名称:TmpQkjvipMemberBasicEntity.java
 * 包名称:com.platform.modules.tmp.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-20 14:33:13        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2020-08-20 14:33:13
 */
@Data
@TableName("TMP_QKJVIP_MEMBER_BASIC")
public class TmpQkjvipMemberBasicEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String memberId;
    /**
     * 手机号
     */
    private String cellphone;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 类型
     */
    private String typename;
    /**
     * 等级
     */
    private String gradename;
    /**
     * 积分
     */
    private Integer integral;
    /**
     * 余额
     */
    private String realtotalprice;
    /**
     * 注册时间
     */
    private String createdate;
    /**
     * 性别
     */
    private String sex;
    /**
     * 所属专卖店
     */
    private String shopname;

    /**
     * isfail
     */
    private Integer isfail;

    /**
     * crm_memberid
     */
    private String crmMemberid;

    /**
     * userid
     */
    private String userid;

    /**
     * province
     */
    private String province;

    /**
     * city
     */
    private String city;

    /**
     * 注册平台类型ID
     */
    private Integer registerplat;

    /**
     * 注册平台类型
     */
    private String registersource;

}
