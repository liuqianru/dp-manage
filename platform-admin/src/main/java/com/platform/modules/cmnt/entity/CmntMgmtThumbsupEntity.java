/*
 * 项目名称:platform-plus
 * 类名称:CmntMgmtThumbsupEntity.java
 * 包名称:com.platform.modules.cmnt.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-25 17:16:10        liuqianru     初版做成
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
 * @date 2021-05-25 17:16:10
 */
@Data
@TableName("CMNT_MGMT_THUMBSUP")
public class CmntMgmtThumbsupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 主表id
     */
    private String mainid;
    /**
     * 评论表id
     */
    private String commentid;
    /**
     * 点赞人
     */
    private String memberid;
    /**
     * 点赞时间
     */
    private Date createdate;
    /**
     * 点赞标识
     */
    private String openid;
}
