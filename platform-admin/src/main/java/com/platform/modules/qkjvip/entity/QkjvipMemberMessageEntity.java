/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberMessageEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-22 11:05:08        李鹏军     初版做成
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
 * @author 李鹏军
 * @date 2020-12-22 11:05:08
 */
@Data
@TableName("QKJVIP_MEMBER_MESSAGE")
public class QkjvipMemberMessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 发送类型id
     */
    private String categoryId;
    /**
     * 发送类型(1：活动 2：积分 3：优惠券）
     */
    private String categoryType;
    /**
     * 渠道
     */
    @NotBlank(message = "渠道不可为空", groups = {AddGroup.class, UpdateGroup.class})
    private String channels;
    /**
     * 短信内容
     */
    private String dxContent;
    /**
     * 微信内容
     */
    private String wxContent;
    /**
     * url
     */
    private String url;
    /**
     * 小程序url
     */
    private String appletsurl;
    /**
     * 小程序参数
     */
    private String appletsparam;
    /**
     * title
     */
    @NotBlank(message = "标题不可为空", groups = {AddGroup.class, UpdateGroup.class})
    private String title;
    /**
     * 封面图
     */
    private String coverImage;
    /**
     * 消息底部链接图
     */
    private String linkImage;
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
     * 短信模板
     */
    private Integer dxTemplate;
    /**
     * 发送方式
     */
    private String checktype;

    @TableField(exist = false)
    private List<String> appidList;

    @TableField(exist = false)
    private Integer sureLayer;
}
