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

/**
 * 地图
 *
 * @author liuqianru
 * @date 2021-09-15 10:51:12
 */
@Data
public class QkjrptReportSalesAreaLevelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /*检索条件 start*/
    private Date starttime;
    private Date endtime;
    private String areacode;
    private String membergroup;
    private String identitygroup;
    private String productname;
    private String listorgno;  // 用户的权限部门
    /*检索条件 end*/

    /*返回结果 start*/
    private String identitylevel;
    private String identitylevelname;
    private Double hwdepamount; // 高端酒
    private Double prdepamount; // 大客户
    private Double funcdepamount;  // 职能部门销售额
    private Double dividepamount;  // 事业部销售额
    /*返回结果 end*/

    //孙珊珊
    private Double scale1;
    private Double scale2;
    private Double scale3;
}
