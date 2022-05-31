/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberQuickqueryEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-26 14:05:17        liuqianru     初版做成
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
 * @date 2021-07-26 14:05:17
 */
@Data
@TableName("QKJVIP_MEMBER_QUICKQUERY")
public class QkjvipMemberQuickqueryEntity implements Serializable {
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
     * 查询条件
     */
    private String myquery;
    /**
     * addUser
     */
    private String adduser;
    /**
     * addTime
     */
    private Date addtime;
}
