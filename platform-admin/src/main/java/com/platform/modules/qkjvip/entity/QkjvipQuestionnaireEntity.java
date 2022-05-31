/*
 * 项目名称:platform-plus
 * 类名称:QkjvipQuestionnaireEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-16 10:27:08        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-07-16 10:27:08
 */
@Data
@TableName("QKJVIP_QUESTIONNAIRE")
public class QkjvipQuestionnaireEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 活动标题
     */
    private String title;
    /**
     * addTime
     */
    private Date addtime;
    /**
     * addUser
     */
    private String adduser;
    /**
     * 是否登录小程序
     */
    private Integer islogin;
    /**
     * 奖励方式
     */
    private Integer rewardtype;
    /**
     * 问题列表
     */
    @TableField(exist = false)
    private List<QkjvipQuestionnaireQuestionEntity> questionlist;
}
