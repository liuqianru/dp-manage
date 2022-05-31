/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareMemberEntity.java
 * 包名称:com.platform.modules.qkjrts.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
@Data
@TableName("QKJRTS_WELFARE_MEMBER")
public class QkjrtsWelfareMemberEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * 主表福利设置id
     */
    private String mainid;
    /**
     * 会员id
     */
    private String memberId;
    /**
     * 通知状态
     */
    private Integer notifystatus;

    @TableField(exist = false)
    private String memberName;
    @TableField(exist = false)
    private String realName;
    @TableField(exist = false)
    private String mobile;
}
