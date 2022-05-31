/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportGroupdistributeEntity.java
 * 包名称:com.platform.modules.qkjrpt.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-11 15:11:18        hanjie     初版做成
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
 * @author hanjie
 * @date 2021-11-11 15:11:18
 */
@Data
@TableName("QKJRPT_REPORT_GROUPDISTRIBUTE")
public class QkjrptReportGroupdistributeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * 团购商名称
     */
    private String distributername;
    /**
     * 团购商类型：0 团购商 1 核心店 2 异业联盟店
     */
    private Integer distributetype;
    /**
     * 所属部门
     */
    private String grouporg;
    /**
     * 一级区域
     */
    private String primaryarea;
    /**
     * 二级区域
     */
    private String secondaryarea;
    /**
     * 三级区域
     */
    private String tertiaryarea;
    /**
     * 经销商维护人id
     */
    private String maintainerid;

    private String customername;
    private String customerphone;

    private String customeraddress;

    private String distributerid;

    private String createuser; // 添加人
    /**
     * Disabled
     */
    private int disabled;
    /**
     * CreateTime
     */
    private Date createtime;
}
