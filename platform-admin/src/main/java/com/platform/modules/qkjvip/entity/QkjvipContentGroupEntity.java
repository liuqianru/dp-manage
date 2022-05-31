/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentGroupEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-24 15:41:39        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-03-24 15:41:39
 */
@Data
@TableName("QKJVIP_CONTENT_GROUP")
public class QkjvipContentGroupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 组名称
     */
    private String title;
    /**
     * 推送状态
     */
    private Integer pushstatus;
    /**
     * 推送目标标识
     */
    private Integer pushtype;
    /**
     * 指定的检索条件
     */
    @TableField(fill = FieldFill.UPDATE)
    private String myquery;
    /**
     * 传给接口的指定的检索条件
     */
    @TableField(fill = FieldFill.UPDATE)
    private String searchquery;
    /**
     * 定时时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private String fixedtime;
    /**
     * 定时任务编号
     */
    @TableField(fill = FieldFill.UPDATE)
    private String taskno;
    /**
     * 已选人员数
     */
    private Integer membernum;
    /**
     * lm_dept
     */
    private String lmDept;
    /**
     * add_dept
     */
    private String addDept;
    /**
     * lm_time
     */
    private Date lmTime;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * add_user
     */
    private String addUser;
    /**
     * lm_user
     */
    private String lmUser;

    /*详情页查询显示用*/
    @TableField(exist = false)
    private List<QkjvipContentEntity> contentList;
    @TableField(exist = false)
    private List<QkjvipOptionsEntity> channelList;
    @TableField(exist = false)
    private List<QkjvipContentGroupuserEntity> memberList;
    @TableField(exist = false)
    private List<String> appids;
    /* 列表页显示用*/
    @TableField(exist = false)
    private String titles;
    @TableField(exist = false)
    private String channels;
}
