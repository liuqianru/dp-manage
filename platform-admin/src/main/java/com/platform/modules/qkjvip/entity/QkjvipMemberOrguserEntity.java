/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrguserEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 17:33:08        liuqianru     初版做成
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
 * @date 2021-08-04 17:33:08
 */
@Data
@TableName("QKJVIP_MEMBER_ORGUSER")
public class QkjvipMemberOrguserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * UserName
     */
    private String username;
    /**
     * Remark
     */
    private String remark;
    /**
     * MemberId
     */
    private String memberid;
    /**
     * IsMain
     */
    private Boolean ismain;
    /**
     * CreateOn
     */
    private Date createon;
    /**
     * Disabled
     */
    private Boolean disabled;
    /**
     * UserId
     */
    private String userid;

    @TableField(exist = false)
    private String orgno;
    @TableField(exist = false)
    private String orgname;
}
