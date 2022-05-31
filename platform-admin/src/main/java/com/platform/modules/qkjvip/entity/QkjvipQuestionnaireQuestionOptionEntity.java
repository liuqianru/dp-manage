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

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-07-16 10:27:08
 */
@Data
public class QkjvipQuestionnaireQuestionOptionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String number;
    /**
     * 选项
     */
    private String content;
    /**
     * 是否是正确答案
     */
    private Boolean isright;
}
