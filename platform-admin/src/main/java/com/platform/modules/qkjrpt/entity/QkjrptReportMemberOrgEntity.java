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

import com.platform.modules.qkjvip.entity.QkjvipMemberOrgEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 客户统计-大区实体
 *
 * @author liuqianru
 * @date 2021-09-15 10:51:12
 */
@Data
public class QkjrptReportMemberOrgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /*检索条件 start*/
    private String powerOrgs;  // 权限部门
    private String currentUserId;  // 当前登录人
    /*检索条件 end*/

    /*返回结果 start*/
    private String virtualDep;  // 客户所属
    private String memberCount;  // 客户所属部门人数
    /*返回结果 end*/
}
