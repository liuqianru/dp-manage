/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberRightsEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-26 16:38:38        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-04-26 16:38:38
 */
@Data
@TableName("QKJVIP_MEMBER_RIGHTS")
public class QkjvipMemberRightsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * 会员等级Id
     */
    private Integer memberlevel;
    /**
     * 会员等级描述
     */
    private String memberlevelname;
    /**
     * 会员权益Id
     */
    private Integer memberright;
    /**
     * 会员权益描述
     */
    private String memberrightname;
    /**
     * 权益值
     */
    private BigDecimal rightvalue;
    /**
     * 是否有权益
     */
    private Boolean ishave;
    /**
     * 是否删除
     */
    private Boolean disabled;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createon;
    /**
     * 更信人
     */
    private String updatename;
    /**
     * 更新时间
     */
    private Date updatetime;
}
