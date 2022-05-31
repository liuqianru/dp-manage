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
public class QkjrptReportSalesAreaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /*检索条件 start*/
    private Date starttime;
    private Date endtime;
    private Integer identitylevel;
    private String membergroup;
    private String listorgno;  // 用户的权限部门
    /*检索条件 end*/

    /*返回结果 start*/
    private String areaname;
    private String areacode;
    private Double amount;
    /*返回结果 end*/

    //孙珊珊增加
    private Double activityamount;
    private Double scale;
    private String productName;
    private Double sumMoney;
}
