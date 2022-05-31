/*
 * 项目名称:platform-plus
 * 类名称:MemberPortraitsettingEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-06-28 09:46:33        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-06-28 09:46:33
 */
@Data
@TableName("QKJVIP_MEMBER_PORTRAITSETTING")
public class MemberPortraitsettingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 购买期间
     */
    private Integer daterate;
    /**
     * 购买笔数
     */
    private Integer buynum;
    /**
     * 购买金额
     */
    private Double buyamount;
    /**
     * 价值段json串
     */
    private String amountrange;
    /**
     * 设置类型（1：高频 2：高价值，3：价值分布）
     */
    private Integer settingtype;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 创建人
     */
    private String createuser;
    /**
     * 修改时间
     */
    private Date lmdate;
    /**
     * 修改人
     */
    private String lmuser;

}
