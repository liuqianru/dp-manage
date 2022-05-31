/*
 * 项目名称:platform-plus
 * 类名称:SysUserChannelEntity.java
 * 包名称:com.platform.modules.sys.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-09 11:32:32        李鹏军     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2020-12-09 11:32:32
 */
@Data
@TableName("SYS_USER_CHANNEL")
public class SysUserChannelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 渠道ID
     */
    private Integer channelId;

    @TableField(exist = false)
    private String appid;
    @TableField(exist = false)
    private String servicename;
}
