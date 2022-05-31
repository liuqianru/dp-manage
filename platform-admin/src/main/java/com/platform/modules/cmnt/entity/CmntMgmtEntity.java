/*
 * 项目名称:platform-plus
 * 类名称:CmntMgmtEntity.java
 * 包名称:com.platform.modules.cmnt.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-25 17:16:11        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.cmnt.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-05-25 17:16:11
 */
@Data
@TableName("CMNT_MGMT")
public class CmntMgmtEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 评论类型
     */
    private String cmnttype;
    /**
     * 类别id(活动id、图文id等)
     */
    private String categoryid;
    /**
     * 是否审核（0：否 1：是）
     */
    private Integer isapproved;
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
}
