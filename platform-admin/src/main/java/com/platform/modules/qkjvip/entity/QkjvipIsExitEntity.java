/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivityEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-03 09:49:29        孙珊珊     初版做成
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
import java.util.List;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2020-09-03 09:49:29
 */
@Data
@TableName("QKJVIP_MEMBER_ACTIVITY")
public class QkjvipIsExitEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String atype;

    @TableField(exist = false)
    private String htmlurl;

}
