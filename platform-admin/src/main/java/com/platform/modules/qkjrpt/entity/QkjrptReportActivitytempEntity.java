/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportActivitytempEntity.java
 * 包名称:com.platform.modules.qkjrpt.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 13:05:30        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-09-15 13:05:30
 */
@Data
@TableName("QKJRPT_REPORT_ACTIVITYTEMP")
public class QkjrptReportActivitytempEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * MemberGroupName
     */
    private String membergroupname;
    /**
     * AreaName
     */
    private String areaname;
    /**
     * ActivityTypeName
     */
    private String activitytypename;
    /**
     * DepNo
     */
    private String depno;
    /**
     * IdentityLevel
     */
    private Integer identitylevel;
    /**
     * CustomerName
     */
    private String customername;
    /**
     * CreateOn
     */
    private String createon;
    /**
     * DepName
     */
    private String depname;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * UnitPrice
     */
    private BigDecimal unitprice;
    /**
     * MemberLevel
     */
    private Integer memberlevel;
    /**
     * ActivityType
     */
    private Integer activitytype;
    /**
     * IdentityLevelName
     */
    private String identitylevelname;
    /**
     * Amount
     */
    private BigDecimal amount;
    /**
     * Mobile
     */
    private String mobile;
    /**
     * ProductName
     */
    private String productname;
    /**
     * ProductNum
     */
    private BigDecimal productnum;
    /**
     * MemberLevelName
     */
    private String memberlevelname;
    /**
     * MemberGroup
     */
    private String membergroup;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * AreaCode
     */
    private String areacode;

    private String identitygroup;

    private String identitygroupname;

    @TableField(exist = false)
    private Integer pertime;
    @TableField(exist = false)
    private Integer pertime1;
    @TableField(exist = false)
    private Integer pertime2;
    @TableField(exist = false)
    private Integer pertime3;
    @TableField(exist = false)
    private Integer pertime4;

    @TableField(exist = false)
    private Double totalcost;
    @TableField(exist = false)
    private Double totalcost1;
    @TableField(exist = false)
    private Double totalcost2;
    @TableField(exist = false)
    private Double totalcost3;
    @TableField(exist = false)
    private Double totalcost4;


    // 查询条件
    @TableField(exist = false)
    private String startregtime;
    @TableField(exist = false)
    private String endregtime;
    @TableField(exist = false)
    private String listorgno;  // 用户的权限部门

    @TableField(exist = false)
    private String monthscale;
    @TableField(exist = false)
    private String yearscale;
    @TableField(exist = false)
    private Integer monthnow;
    @TableField(exist = false)
    private Integer plannum;
    @TableField(exist = false)
    private Integer monthup;
    @TableField(exist = false)
    private Integer yearup;

}
