/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentPushchannelEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-24 15:41:46        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-03-24 15:41:46
 */
@Data
@TableName("QKJVIP_CONTENT_PUSHCHANNEL")
public class QkjvipContentPushchannelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 内容组id
     */
    private String groupid;
    /**
     * 会员联系方式
     */
    private String appid;
    /**
     *
     */
    private String appname;
}
