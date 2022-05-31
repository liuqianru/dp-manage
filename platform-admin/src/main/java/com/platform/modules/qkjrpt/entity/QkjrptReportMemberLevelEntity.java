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

/**
 * 客户统计-大区-等级实体
 *
 * @author liuqianru
 * @date 2021-09-15 10:51:12
 */
@Data
public class QkjrptReportMemberLevelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String identitylevel;
    private String areaCode;
    private Integer cnt; // 人数
}
