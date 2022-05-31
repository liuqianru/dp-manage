/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawResultcachEntity.java
 * 包名称:com.platform.modules.qkjluck.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-18 14:27:03             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjluck.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 
 * @date 2021-11-18 14:27:03
 */
@Data
@TableName("QKJLUCK_DRAW_RESULTCACH")
public class QkjluckDrawResultcachEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * addtime
     */
    private Date addtime;
    /**
     * id
     */
    @TableId
    private String id;
    /**
     * key
     */
    private String ckey;
    /**
     * value
     */
    private String cvalue;

    private Integer isnormal;
}
