/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActothersignEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-26 15:07:44        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-04-26 15:07:44
 */
@Data
@TableName("QKJVIP_MEMBER_ACTOTHERSIGN")
public class QkjvipMemberActothersignEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * addtime
     */
    private Date addtime;
    /**
     * member_name
     */
    private String memberName;
    /**
     * phone
     */
    private String phone;
    /**
     * id
     */
    @TableId
    private String id;
    /**
     * batch_num
     */
    private Integer batchNum;
    /**
     * member_id
     */
    private String memberId;
    /**
     * address
     */
    private String address;
    /**
     * act_id
     */
    private String actId;
}
