/*
 * 项目名称:platform-plus
 * 类名称:MemberActiveEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/13 13:29            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * MemberActiveEntity
 *
 * @author liuqianru
 * @date 2020/3/13 13:29
 */
@Data
@TableName("qkjvip_member_active")
public class MemberActiveEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String uuid;
    /**
     * 会员id
     */
    private String memberId;
    /**
     * 联系方式
     */
    @NotBlank(message = "联系方式不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;
    /**
     * 标题
     */
    @NotBlank(message = "活动标题不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String title;

    /**
     * 活动开始时间
     */
    @NotBlank(message = "开始时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String activeStart;
    /**
     * 活动结束时间
     */
    @NotBlank(message = "结束时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String activeEnd;
    /**
     * 活动类型
     */
    @NotBlank(message = "活动类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String activeType;
    /**
     * 活动地点
     */
    private String activePlace;
    /**
     * 是否邀请
     */
    private Integer isInvite;
    /**
     * 是否生成订单
     */
    private Integer isOrder;
    /**
     * 是否到场
     */
    private Integer isAttend;
    /**
     * 到场人数
     */
    private String attendNum;
    /**
     * 是否重点跟进
     */
    private Integer isFollowup;
    /*
     * 添加人
     */
    private String addUser;
    /*
     * 添加部门
     */
    private String addDept;
    /*
     * 添加时间
     */
    private Date addTime;
}
