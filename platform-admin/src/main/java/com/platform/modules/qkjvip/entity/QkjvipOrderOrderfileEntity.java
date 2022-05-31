/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderOrderfileEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-10 11:37:09        孙珊珊     初版做成
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
 * @author 孙珊珊
 * @date 2021-03-10 11:37:09
 */
@Data
@TableName("QKJVIP_ORDER_ORDERFILE")
public class QkjvipOrderOrderfileEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * OrderId
     */
    private String orderid;
    /**
     * FileName
     */
    private String filename;
    /**
     * FileUrl
     */
    private String fileurl;
    /**
     * FileType
     */
    private Integer filetype;
    /**
     * Creator
     */
    private String creator;
    /**
     * CreateTime
     */
    private Date createtime;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * Remark
     */
    private String remark;
}
