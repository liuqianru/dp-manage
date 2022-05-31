/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitGivedetailEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-24 09:16:08        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-08-24 09:16:08
 */
@Data
@TableName("QKJVIP_MEMBER_VISIT_GIVEDETAIL")
public class QkjvipMemberVisitGivedetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 赠酒主表id
     */
    private String mainid;
    /**
     * 赠酒酒品json串
     */
    private String detail;
    /**
     * memberId
     */
    private String memberid;
    /**
     * mobile
     */
    private String mobile;
    /**
     * addDept
     */
    private String adddept;
    /**
     * addTime
     */
    private Date addtime;
    /**
     * addUser
     */
    private String adduser;

    @TableField(exist = false)
    private String membername;
}
