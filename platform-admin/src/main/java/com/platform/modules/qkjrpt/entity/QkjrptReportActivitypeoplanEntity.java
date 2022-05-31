/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportActivitypeoplanEntity.java
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
@TableName("QKJRPT_REPORT_ACTIVITYPEOPLAN")
public class QkjrptReportActivitypeoplanEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 计划人次
     */
    private Integer plannum;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * CreateOn
     */
    private String createon;
    /**
     * ActivityType
     */
    private Integer activitytype;
    /**
     * Remark
     */
    private String remark;
    /**
     * IdentityLevelName
     */
    private String identitylevelname;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * AreaCode
     */
    private String areacode;
    /**
     * IdentityLevel
     */
    private Integer identitylevel;
    /**
     * ActivityTypeName
     */
    private String activitytypename;
    /**
     * AreaName
     */
    private String areaname;
}
