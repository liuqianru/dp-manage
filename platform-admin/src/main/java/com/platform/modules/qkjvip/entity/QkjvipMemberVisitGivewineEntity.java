/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitGivewineEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-24 09:16:08        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-08-24 09:16:08
 */
@Data
@TableName("QKJVIP_MEMBER_VISIT_GIVEWINE")
public class QkjvipMemberVisitGivewineEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 赠酒目的
     */
    private String purpose;
    /**
     * 赠酒时间
     */
    private Date visittime;
    /**
     * 类型(1：日常赠酒 2：节日赠酒）
     */
    private Integer type;
    /**
     * 赠酒说明
     */
    private String remarks;
    /**
     * 状态（0：申请中 1：已签批）
     */
    private Integer status;
    /**
     * 人数
     */
    private Integer number;
    /**
     * addDept
     */
    private String adddept;
    /**
     * lmDept
     */
    private String lmdept;
    /**
     * lmUser
     */
    private String lmuser;
    /**
     * lmTime
     */
    private Date lmtime;
    /**
     * totalAmount
     */
    private BigDecimal totalamount;
    /**
     * addUser
     */
    private String adduser;
    /**
     * addTime
     */
    private Date addtime;
    /**
     * 拜访方式
     */
    private String visittype;
    /**
     * 赠酒详细列表
     */
    @TableField(exist = false)
    private List<QkjvipMemberVisitGivedetailEntity> detaillist;
    /**
     * 签批文件
     */
    @TableField(exist = false)
    private List<QkjvipMemberVisitFilesEntity> filelist;
}
