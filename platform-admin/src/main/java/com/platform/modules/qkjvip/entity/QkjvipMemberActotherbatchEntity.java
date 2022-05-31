/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActotherbatchEntity.java
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
@TableName("QKJVIP_MEMBER_ACTOTHERBATCH")
public class QkjvipMemberActotherbatchEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * totlepeoplenum
     */
    private Integer totlepeoplenum;
    /**
     * mdytime
     */
    private Date mdytime;
    /**
     * main_id
     */
    @TableId
    private String mainId;
    /**
     * batch_num
     */
    private Integer batchNum;
    /**
     * mdyuser
     */
    private String mdyuser;
}
