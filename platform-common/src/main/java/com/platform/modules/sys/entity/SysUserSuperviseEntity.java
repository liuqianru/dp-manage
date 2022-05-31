/*
 * 项目名称:platform-plus
 * 类名称:SysUserSuperviseEntity.java
 * 包名称:com.platform.modules.sys.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 09:26:18        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.entity;

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
 * @date 2021-08-04 09:26:18
 */
@Data
@TableName("SYS_USER_SUPERVISE")
public class SysUserSuperviseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * org_no
     */
    private String orgNo;
    /**
     * 本部门权限（0：无 1：有）
     */
    private Boolean selfchecked;
    /**
     * 子部门权限（0：无 1：有）
     */
    private Boolean subchecked;
    /**
     * addTime
     */
    private Date addtime;
    /**
     * addUser
     */
    private String adduser;

    @TableField(exist = false)
    private String orgName;
}
