/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportActivityseatplanEntity.java
 * 包名称:com.platform.modules.qkjrpt.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-28 11:31:01        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-09-28 11:31:01
 */
@Data
@TableName("QKJRPT_REPORT_ACTIVITYSEATPLAN")
public class QkjrptReportActivityseatplanEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 计划人次
     */
    private Integer plannum;
    /**
     * ActivityTypeName
     */
    private String activitytypename;
    /**
     * CreateOn
     */
    private String createon;
    /**
     * Remark
     */
    private String remark;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * ActivityType
     */
    private Integer activitytype;
    /**
     * Disabled
     */
    private Boolean disabled;
}
