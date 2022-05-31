/*
 * 项目名称:platform-plus
 * 类名称:QkjmHProcessEntity.java
 * 包名称:com.platform.modules.qkjm.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-02 16:26:02        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-11-02 16:26:02
 */
@Data
@TableName("QKJM_H_PROCESS")
public class QkjmHProcessEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId
    private String uuid;
    /**
     * 类型0订单管理
     */
    private Integer processId;
    /**
     * 业务ID
     */
    private String bizId;
    /**
     * 业务时间
     */
    private String bizUser;
    /**
     * 业务人
     */
    private Date bizTime;
    /**
     * 业务备注
     */
    private String bizNote;
    /**
     * 业务状态1
     */
    private Integer bizStatus01;
    /**
     * 业务状态2
     */
    private Integer bizStatus02;
    /**
     * 业务状态3
     */
    private Integer bizStatus03;
    /**
     * 业务状态4
     */
    private Integer bizStatus04;
    /**
     * 业务状态5
     */
    private Integer bizStatus05;
    /**
     * 业务标记
     */
    private String bizSign;
}
