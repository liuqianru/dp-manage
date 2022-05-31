/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareEntity.java
 * 包名称:com.platform.modules.qkjrts.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:28        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.entity;

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
 * @date 2022-03-25 09:09:28
 */
@Data
@TableName("QKJRTS_WELFARE")
public class QkjrtsWelfareEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 创建人
     */
    private String createuser;
    /**
     * 发送状态(0：未发送 1：已发送)
     */
    private Integer sendstatus;
    /**
     * 发送时间
     */
    private Date sendtime;
    /**
     * 是否已删除(0：否 1：是）
     */
    private Integer disabled;
    /**
     * CreateTime
     */
    private Date createtime;
    /**
     * 场景类型（0：后台手动推 1：生日推）
     */
    private Integer scenetype;
    /**
     * 由核心店指定会员
     */
    private Boolean checkedstatus1;
    /**
     * 由其他管理员指定会员
     */
    private Boolean checkedstatus2;
    /**
     * 由核心店指定会员有效期
     */
    private Date expiresdate1;
    /**
     * 由其他管理员指定会员有效期
     */
    private Date expiresdate2;
    /**
     * 发放方式
     */
    private String checktype;
    /**
     * 积分
     */
    @TableField(exist = false)
    private Integer integral;

    @TableField(exist = false)
    private List<QkjrtsWelfareOtherobjectsEntity> objectslist1;
    @TableField(exist = false)
    private List<QkjrtsWelfareOtherobjectsEntity> objectslist2;
    @TableField(exist = false)
    private List<QkjrtsWelfareMemberEntity> memberlist;
    @TableField(exist = false)
    private List<QkjrtsWelfareDetailsEntity> welfarelist;
}
