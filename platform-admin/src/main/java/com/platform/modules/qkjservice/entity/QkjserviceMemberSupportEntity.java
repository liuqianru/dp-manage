/*
 * 项目名称:platform-plus
 * 类名称:QkjserviceMemberSupportEntity.java
 * 包名称:com.platform.modules.qkjservice.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-09 09:19:43        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjservice.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-09-09 09:19:43
 */
@Data
@TableName("QKJSERVICE_MEMBER_SUPPORT")
public class QkjserviceMemberSupportEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 服务客户
     */
    private String memberid;
    /**
     * membername
     */
    private String membername;
    /**
     * 服务项目
     */
    private String title;
    /**
     * 服务内容
     */
    private String content;
    /**
     * 费用
     */
    private Double price;
    /**
     * 备注
     */
    private String remark;
    /**
     * addtime
     */
    private Date addtime;
    /**
     * adduser
     */
    private String adduser;
}
