/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntegralEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-21 15:18:24        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2020-12-21 15:18:24
 */
@Data
@TableName("QKJVIP_MEMBER_INTEGRAL")
public class QkjvipMemberIntegralEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * title
     */
    private String title;
    /**
     * 要发放的积分
     */
    @NotBlank(message = "积分不可为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer integral;
    /**
     * add_user
     */
    private String addUser;
    /**
     * add_dept
     */
    private String addDept;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * lm_user
     */
    private String lmUser;
    /**
     * lm_dept
     */
    private String lmDept;
    /**
     * lm_time
     */
    private Date lmTime;

    /**
     * 积分发放状态(0：未发 1：积分已发 2：消息已发）
     */
    private Integer sendStatus;

    @TableField(exist = false)
    private List<QkjvipMemberIntegraluserEntity> memberlist;
    @TableField(exist = false)
    private String realName;
    @TableField(exist = false)
    private String orgName;
}
