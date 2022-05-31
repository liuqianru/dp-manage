/*
 * 项目名称:platform-plus
 * 类名称:CmntMgmtCommentEntity.java
 * 包名称:com.platform.modules.cmnt.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-25 17:16:11        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.cmnt.entity;

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
 * @date 2021-05-25 17:16:11
 */
@Data
public class CmntMgmtCommentResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private CmntMgmtCommentUserEntity commentuser;

    private String content;

    private Date createdate;

    private Integer thumbsupcnt;

    private Boolean isthumbsup;

    private List<CmntMgmtCommentChildrenEntity> childrenlist;

}
