/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-23 16:37:04        liuqianru     初版做成
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
 * @date 2021-03-23 16:37:04
 */
@Data
@TableName("QKJVIP_CONTENT")
public class QkjvipContentEntity implements Serializable {
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
     * 封面图片
     */
    private String coverimage;
    /**
     * 图文内容
     */
    private String content;
    /**
     * 推送状态
     */
    private Integer pushstatus;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * add_dept
     */
    private String addDept;
    /**
     * lm_dept
     */
    private String lmDept;
    /**
     * lm_user
     */
    private String lmUser;
    /**
     * add_user
     */
    private String addUser;
    /**
     * lm_time
     */
    private Date lmTime;

    /**
     * 排序
     */
    @TableField(exist = false)
    private Integer sortvalue;
}
