/*
 * 项目名称:platform-plus
 * 类名称:QkjvipTaglibsEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-26 14:20:07        liuqianru     初版做成
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
 * @date 2020-08-26 14:20:07
 */
@Data
@TableName("QKJVIP_TAGLIBS")
public class QkjvipTaglibsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String tagId;
    /**
     * 标签名
     */
    private String tagName;
    /**
     * add_dept
     */
    private String addDept;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * add_user
     */
    private String addUser;
    /**
     * 标签组id
     */
    private String tagGroupId;

    @TableField(exist = false)
    private String tagGroupName;
    @TableField(exist = false)
    private Integer tagType;
    @TableField(exist = false)
    private Integer optiontype;
}
