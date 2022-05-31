/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawAcitiityitemEntity.java
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

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-07-05 17:26:24
 */
@Data
@TableName("QKJLUCK_DRAW_ACITIITYITEM")
public class QkjluckDrawAcitiityitemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 奖品数量0无穷大
     */
    private Integer number;
    /**
     * id
     */
    @TableId
    private String id;
    /**
     * prizetakentype
     */
    private String prizetakentype;
    /**
     * activity_id
     */
    private String activityId;
    /**
     * sub_name
     */
    private String subName;
    /**
     * prizetakenpath
     */
    private String prizetakenpath;
    /**
     * name
     */
    private String name;

    /**
     * 抽奖色块颜色
     */
    private String divcolor;

    private String divimg;// 抽奖色块图片

    private Integer shownum;

    private Double weight;

    private String prizetakenpathresult;

    private Integer pronum;

    private String takedate;


}
