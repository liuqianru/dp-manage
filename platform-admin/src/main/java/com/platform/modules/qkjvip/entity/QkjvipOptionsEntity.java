/*
 * 项目名称:platform-plus
 * 类名称:QkjvipTaglibsEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-26 14:20:07        liuqianru     初版做成
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
 * @date 2020-12-25 14:20:07
 */
@Data
public class QkjvipOptionsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String appid;
    private String name;
}
