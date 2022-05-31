/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawResultEntity.java
 * 包名称:com.platform.modules.qkjluck.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-05 17:26:24        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjluck.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-07-05 17:26:24
 */
@Data
@TableName("QKJLUCK_DRAW_RESULT")
public class QkjluckDrawResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * addtime
     */
    private Date addtime;
    /**
     * activity_id
     */
    private String activityId;
    /**
     * item_id
     */
    private String itemId;
    /**
     * openid
     */
    private String openid;

    private String unionid;

    private Integer num;

    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String subName;
    @TableField(exist = false)
    private String prizetakentype;
    @TableField(exist = false)
    private String prizetakenpathresult;
    @TableField(exist = false)
    private String takedate;
    @TableField(exist = false)
    private String userid;
    @TableField(exist = false)
    private String mobile;
}
