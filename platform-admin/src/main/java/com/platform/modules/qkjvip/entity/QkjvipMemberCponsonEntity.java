/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberCponsonEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-17 14:46:26        孙珊珊     初版做成
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
 * @author 孙珊珊
 * @date 2020-12-17 14:46:26
 */
@Data
@TableName("QKJVIP_MEMBER_CPONSON")
public class QkjvipMemberCponsonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * main_id
     */
    private String mainId;
    /**
     * member_id
     */
    private String memberId;
    /**
     * cpon_id
     */
    private String cponId;

    private Integer status;

    /**
     * 会员名称
     */
    @TableField(exist = false)
    private String memberName;
    /*
     * 会员真实姓名
     */
    @TableField(exist = false)
    private String realName;
    /*
     * 会员手机
     */
    @TableField(exist = false)
    private String mobile;
}
