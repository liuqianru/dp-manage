/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportMembertempEntity.java
 * 包名称:com.platform.modules.qkjrpt.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 10:51:12        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 客户统计-大区-等级实体
 *
 * @author liuqianru
 * @date 2021-09-15 10:51:12
 */
@Data
public class QkjrptReportMemberAreaLevelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /*检索条件 start*/
    private String startAddTime;
    private String endAddTime;
    private String memberGroup;
    private String areaCode;
    private String groupOrg;
    private String currentUserId; // 当前登录人
    private String powerOrgs; // 权限部门
    /*检索条件 end*/

    /*返回结果 start*/
    private List<QkjrptReportMemberLevelEntity> planList;
    private List<QkjrptReportMemberLevelEntity> actualList;
    private Integer plan; // 目标总人数
    private Integer actual; // 达成总人数
    private Double yoy; // 同比
    private Double mom; // 环比
    /*返回结果 end*/
}
