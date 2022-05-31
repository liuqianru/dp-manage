/*
 * 项目名称:platform-plus
 * 类名称:QkjvipQuestionnaireQuestionEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-16 10:27:08        liuqianru     初版做成
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
import java.util.List;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-07-16 10:27:08
 */
@Data
@TableName("QKJVIP_QUESTIONNAIRE_QUESTION")
public class QkjvipQuestionnaireQuestionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 题目
     */
    private String topic;
    /**
     * 主表id
     */
    private String mainid;
    /**
     * 问题类型
     */
    private Integer questiontype;
    /**
     * 问题选项
     */
    private String questionoptions;
    /**
     * 积分
     */
    private Integer integral;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * addUser
     */
    private String adduser;
    /**
     * addTime
     */
    private Date addtime;
    /**
     * 选项
     */
    @TableField(exist = false)
    private List<QkjvipQuestionnaireQuestionOptionEntity> optionlist;
}
