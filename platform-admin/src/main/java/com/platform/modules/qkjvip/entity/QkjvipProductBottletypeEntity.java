/*
 * 项目名称:platform-plus
 * 类名称:QkjvipProductBottletypeEntity.java
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
@TableName("QKJVIP_PRODUCT_BOTTLETYPE")
public class QkjvipProductBottletypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * ProductId
     */
    private String productid;
    /**
     * Name
     */
    private String name;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * Creator
     */
    private String creator;
    /**
     * CreateTime
     */
    private Date createtime;
    /**
     * Remark
     */
    private String remark;
}
