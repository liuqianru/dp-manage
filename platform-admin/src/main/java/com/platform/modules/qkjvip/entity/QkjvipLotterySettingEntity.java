/*
 * 项目名称:platform-plus
 * 类名称:QkjvipLotterySettingEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-05 10:59:58        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-07-05 10:59:58
 */
@Data
@TableName("QKJVIP_LOTTERY_SETTING")
public class QkjvipLotterySettingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 背景图片
     */
    private String bgimg;
    /**
     * 抽奖人类型
     */
    private Integer usertype;
    /**
     * 奖项
     */
    private String prizes;
    /**
     * 是否允许重复抽奖（0否1是）
     */
    private Integer repeatallowed;
    /**
     * 关联活动id
     */
    @TableField(fill = FieldFill.UPDATE)
    private String activityid;
    /**
     * 关联活动标题
     */
    @TableField(fill = FieldFill.UPDATE)
    private String activitytitle;
    /**
     * 创建人
     */
    private String adduser;
    /**
     * 创建时间
     */
    private Date addtime;

    @TableField(exist = false)
    private List<QkjvipLotteryPrizeEntity> prizelist;
    @TableField(exist = false)
    private List<QkjvipLotteryUsersEntity> userlist;

}
