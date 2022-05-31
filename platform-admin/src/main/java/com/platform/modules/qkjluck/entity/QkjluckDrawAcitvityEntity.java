/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawAcitvityEntity.java
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
import java.util.List;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-07-05 17:26:24
 */
@Data
@TableName("QKJLUCK_DRAW_ACITVITY")
public class QkjluckDrawAcitvityEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * adduser
     */
    private String adduser;
    /**
     * str_date
     */
    private String strDate;
    /**
     * end_date
     */
    private String endDate;
    /**
     * id
     */
    @TableId
    private String id;
    /**
     * title
     */
    private String title;
    /**
     * addtime
     */
    private Date addtime;

    /**
     * 抽奖次数
     */
    private Integer peonum;

    private Integer points;

    private Integer iseveryday;

    private Integer lucktype;

    @TableField(exist = false)
    private List<QkjluckDrawAcitiityitemEntity> itemlist;
}
