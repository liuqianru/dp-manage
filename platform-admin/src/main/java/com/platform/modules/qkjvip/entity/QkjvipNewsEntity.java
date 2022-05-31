/*
 * 项目名称:platform-plus
 * 类名称:QkjvipNewsEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-10 09:37:42        liuqianru     初版做成
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
 * @author liuqianru
 * @date 2021-05-10 09:37:42
 */
@Data
@TableName("QKJVIP_NEWS")
public class QkjvipNewsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面图片
     */
    private String coverimage;
    /**
     * 图文内容
     */
    private String content;
    /**
     * 作者
     */
    private String author;
    /**
     * 阅读数
     */
    private Integer readnum;
    /**
     * 概要
     */
    private String description;
    /**
     * 新闻模块
     */
    private String module;
    /**
     * 是否开启评论（0：否 1：是）
     */
    private Boolean iscomment;
    /**
     * 是否开启审核（0：否 1：是）
     */
    private Boolean isapproved;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * lm_time
     */
    private Date lmTime;
    /**
     * lm_user
     */
    private String lmUser;
    /**
     * add_user
     */
    private String addUser;
    /**
     * add_dept
     */
    private String addDept;
    /**
     * lm_dept
     */
    private String lmDept;

    @TableField(exist = false)
    private boolean isthumbsup;
    @TableField(exist = false)
    private Integer thumbsupcnt;
    @TableField(exist = false)
    private String memberid; // 文章点赞用条件
    @TableField(exist = false)
    private String openid;  // 文章点赞用条件
}
