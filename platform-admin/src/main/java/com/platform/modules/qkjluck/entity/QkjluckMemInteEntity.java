/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawResultEntity.java
 * 包名称:com.platform.modules.qkjluck.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-05 17:26:24        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjluck.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-07-05 17:26:24
 */
@Data
public class QkjluckMemInteEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * activity_id
     */
    private String crmmemberid;
    /**
     * item_id
     */
    private String remark;

    private Integer actiontype;

    private Integer integral;
}
