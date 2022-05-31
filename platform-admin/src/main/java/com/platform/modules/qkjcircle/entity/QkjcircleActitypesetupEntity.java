/*
 * 项目名称:platform-plus
 * 类名称:QkjcircleActitypesetupEntity.java
 * 包名称:com.platform.modules.qkjcircle.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-11 11:21:03        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcircle.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-08-11 11:21:03
 */
@Data
@TableName("QKJCIRCLE_ACTITYPESETUP")
public class QkjcircleActitypesetupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * circlelist
     */
    @TableField(value = "circlelist", strategy = FieldStrategy.IGNORED)
    private String circlelist;
    /**
     * id
     */
    @TableId
    private String id;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * add_user
     */
    private String addUser;
    /**
     * activity_type
     */
    private String activityType;

    @TableField(exist = false)
    private String dictid;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private List<String> tagIdList;
    @TableField(exist = false)
    private String activityValue;

}
