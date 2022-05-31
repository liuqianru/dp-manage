/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberRightsEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-26 16:38:38        liuqianru     初版做成
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
 * @date 2021-04-26 16:38:38
 */
@Data
public class QkjvipMemberRightsResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会员等级Id
     */
    private Integer memberlevel;
    /**
     * 会员等级描述
     */
    private String memberlevelname;
    /**
     * 会员折扣
     */
    private BigDecimal rightvalue;

    @TableField(exist = false)
    private List<QkjvipMemberRightsEntity> rightsList;

    @TableField(exist = false)
    private List<Integer> checkedList;
}
