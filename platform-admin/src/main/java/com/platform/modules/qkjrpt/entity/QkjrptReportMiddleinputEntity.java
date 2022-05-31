/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportMiddleinputEntity.java
 * 包名称:com.platform.modules.qkjrpt.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-16 09:36:36        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.entity;

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
 * @date 2021-11-16 09:36:36
 */
@Data
@TableName("QKJRPT_REPORT_MIDDLEINPUT")
public class QkjrptReportMiddleinputEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物料id
     */
    private String materialid;
    /**
     * 主表id
     */
    private String mainId;
    /**
     * 物料名称
     */
    private String name;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 单位
     */
    private String unit;
    /**
     * 备注
     */
    private String content;
    /**
     * 单价
     */
    private Double unitprice;
    /**
     * 总价
     */
    private BigDecimal totalprice;
    /**
     * 0活动投入1拜访投入2.活动个人赠品
     */
    private Integer type;
    /**
     * 会员id
     */
    private String memberid;
    /**
     * id
     */
    @TableId
    private String id;
    /**
     * add_time
     */
    private Date addTime;
}
