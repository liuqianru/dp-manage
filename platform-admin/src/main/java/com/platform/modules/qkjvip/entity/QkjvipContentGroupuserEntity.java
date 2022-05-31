/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentGroupuserEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-31 17:47:01        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-03-31 17:47:01
 */
@Data
@TableName("QKJVIP_CONTENT_GROUPUSER")
public class QkjvipContentGroupuserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 积分id
     */
    private String groupid;
    /**
     * 会员id
     */
    private String memberId;

    @TableField(exist = false)
    private String memberName;
    @TableField(exist = false)
    private String realName;
    @TableField(exist = false)
    private String mobile;

}
