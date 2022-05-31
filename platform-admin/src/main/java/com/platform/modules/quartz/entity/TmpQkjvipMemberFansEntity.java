/*
 * 项目名称:platform-plus
 * 类名称:TmpQkjvipMemberFansEntity.java
 * 包名称:com.platform.modules.tmp.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-14 13:51:21        liuqianru     初版做成
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
 * @date 2020-09-14 13:51:21
 */
@Data
@TableName("TMP_QKJVIP_MEMBER_FANS")
public class TmpQkjvipMemberFansEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 是否取消关注 0 否 1 是
     */
    private Integer isunsubscribe;
    /**
     * 取消关注时间
     */
    private Date unsubscribetime;
    /**
     * 服务号名称
     */
    private String servicename;
    /**
     * 手机号
     */
    private String phoneno;
    /**
     * 用户的标识，对当前公众号唯一
     */
    private String openid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Integer sex;
    /**
     * 用户的语言，简体中文为zh_CN
     */
    private String language;
    /**
     * 用户所在城市
     */
    private String city;
    /**
     * 用户所在省份
     */
    private String province;
    /**
     * 用户所在国家
     */
    private String country;
    /**
     * 用户头像
     */
    private String headimgurl;
    /**
     * 用户关注时间。如果用户曾多次关注，则取最后关注时间
     */
    private Date subscribetime;
    /**
     * 用户更新时间
     */
    private Date updatetime;
    /**
     * UnionId
     */
    private String unionid;
    /**
     * Appid
     */
    private String appid;
}
