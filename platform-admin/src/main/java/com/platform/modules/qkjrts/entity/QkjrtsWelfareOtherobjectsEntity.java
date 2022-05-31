/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareOtherobjectsEntity.java
 * 包名称:com.platform.modules.qkjrts.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-22 14:44:54        liuqianru     初版做成
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
 * @date 2022-04-22 14:44:54
 */
@Data
@TableName("QKJRTS_WELFARE_OTHEROBJECTS")
public class QkjrtsWelfareOtherobjectsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * 主表福利设置id
     */
    private String mainid;
    /**
     * 核心店id
     */
    private String distributerid;
    /**
     * 核心店名字
     */
    private String distributername;
    /**
     * 负责人ID
     */
    private String userid;
    /**
     * 负责人名称
     */
    private String username;
    /**
     * 负责人电话
     */
    private String usermobile;
    /**
     * 发送数量
     */
    private Integer sendnum;
    /**
     * 发送对象类型（1：核心店团购商 2：管理员制定内部部门或人员）
     */
    private Integer objecttype;
    /**
     * 发送时间
     */
    private Date sendtime;
    /**
     * 发送状态
     */
    private Integer sendstatus;
    /**
     * 标题
     */
    @TableField(exist = false)
    private String title;
    /**
     * 创建人
     */
    @TableField(exist = false)
    private String createuser;
    /**
     * CreateTime
     */
    @TableField(exist = false)
    private Date createtime;

    @TableField(exist = false)
    private List<QkjrtsWelfareMemberEntity> memberlist;
}
