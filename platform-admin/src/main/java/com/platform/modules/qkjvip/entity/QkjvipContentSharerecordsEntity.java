/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentSharerecordsEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-01 17:01:46        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-04-01 17:01:46
 */
@Data
@TableName("QKJVIP_CONTENT_SHARERECORDS")
public class QkjvipContentSharerecordsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 会员id+文章id
     */
    private String recordKey;
    /**
     * 1：打开查看 2：分享
     */
    private Integer recordValue;

    @TableField(exist = false)
    private String memberId;
}
