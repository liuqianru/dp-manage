/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberSignupaddressEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-10-15 17:21:03        孙珊珊     初版做成
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
 * @author 孙珊珊
 * @date 2020-10-15 17:21:03
 */
@Data
@TableName("QKJVIP_MEMBER_SIGNUPADDRESS")
public class QkjvipMemberSignupaddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * address
     */
    private String address;
    /**
     * activityid
     */
    private String activityid;
    /**
     * coordinate
     */
    private String coordinate;
}
