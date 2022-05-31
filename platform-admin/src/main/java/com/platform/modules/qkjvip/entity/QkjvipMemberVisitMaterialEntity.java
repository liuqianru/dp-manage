/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitMaterialEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-02 10:50:33        李鹏军     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author 李鹏军
 * @date 2020-12-02 10:50:33
 */
@Data
@TableName("QKJVIP_MEMBER_VISIT_MATERIAL")
public class QkjvipMemberVisitMaterialEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 名称
     */
    @Excel(name = "物料名称", width = 15)
    private String name;
    /**
     * 数量
     */
    @Excel(name = "数量", width = 15)
    private Integer number;
    /**
     * 单位
     */
    @Excel(name = "单位", width = 15)
    private String unit;
    /**
     * 单价
     */
    @Excel(name = "单价", width = 15)
    private Double unitprice;
    /**
     * 备注
     */
    private String content;
    /**
     * add_user
     */
    private String addUser;
    /**
     * add_dept
     */
    private String addDept;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * 拜访id
     */
    private String visitId;
    /**
     * 小计
     */
    private BigDecimal totalprice;
    /**
     * 产品id
     */
    private String productid;

    /**
     * 拜访或活动标题
     */
    @TableField(exist = false)
    private String title;
}
