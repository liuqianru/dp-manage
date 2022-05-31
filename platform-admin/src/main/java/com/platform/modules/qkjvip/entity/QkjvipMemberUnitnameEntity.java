/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberUnitnameEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-17 10:44:55        liuqianru     初版做成
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
 * @date 2021-11-17 10:44:55
 */
@Data
@TableName("QKJVIP_MEMBER_UNITNAME")
public class QkjvipMemberUnitnameEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 单位名称
     */
    @TableId
    private String unitname;
    /**
     * 创建时间
     */
    private Date createtime;
}
