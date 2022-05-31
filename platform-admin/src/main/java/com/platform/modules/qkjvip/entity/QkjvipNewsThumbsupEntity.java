/*
 * 项目名称:platform-plus
 * 类名称:QkjvipNewsThumbsupEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-06-04 11:35:48        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-06-04 11:35:48
 */
@Data
@TableName("QKJVIP_NEWS_THUMBSUP")
public class QkjvipNewsThumbsupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 主表id
     */
    private String newid;
    /**
     * 点赞人
     */
    private String memberid;
    /**
     * 点赞时间
     */
    private Date createdate;
    /**
     * openId
     */
    private String openid;
}
