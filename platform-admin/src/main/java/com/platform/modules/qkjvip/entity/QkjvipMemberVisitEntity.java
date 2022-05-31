/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-01 11:39:36        liuqianru     初版做成
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
 * @date 2020-12-01 11:39:36
 */
@Data
@TableName("QKJVIP_MEMBER_VISIT")
public class QkjvipMemberVisitEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 会员id
     */
    private String memberId;
    /**
     * 会员联系方式
     */
    @NotBlank(message = "联系方式不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;
    /**
     * 拜访开始时间
     */
    private Date visitStartDate;
    /**
     * 拜访结束时间
     */
    private Date visitEndDate;
    /**
     * 拜访类型
     */
    private String visitType;
    /**
     * 拜访状态
     */
    private Integer visitStatus;
    /**
     * 上传图片
     */
    private String images;
    /**
     * 总结
     */
    private String content;
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
     * 日期类型（1：近一个月 2：近3个月 3： 近半年 4：近一年）
     */
    @TableField(exist = false)
    private String dateType;
    /**
     * 会员名称
     */
    @TableField(exist = false)
    private String memberName;

    @TableField(exist = false)
    private List<QkjvipMemberVisitMaterialEntity> materialList;

    @TableField(exist = false)
    private List<QkjvipOrderOrderEntity> orderList;
}
