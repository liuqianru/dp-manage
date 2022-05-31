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

import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-07-05 17:26:24
 */
@Data
public class QkjluckMallInteEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * activity_id
     */
    private String userid;

    private String mobile;
    /**
     * item_id
     */
    private String descr;

    private Integer action;

    private Integer integral;
}
