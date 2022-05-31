/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareBirthdayEntity.java
 * 包名称:com.platform.modules.qkjrts.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-13 12:20:09        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2022-04-13 12:20:09
 */
@Data
@TableName("QKJRTS_WELFARE_BIRTHDAY")
public class QkjrtsWelfareBirthdayEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * 福利类型
     */
    private Integer welfaretype;
    /**
     * 福利id
     */
    private Integer welfareid;
    /**
     * 福利名称
     */
    private String welfarename;
    /**
     * 有效期类型(0：绝对 1：相对)
     */
    private Integer periodtype;
    /**
     * 相对有效期天数
     */
    private Integer perioddays;
    /**
     * 绝对有效期开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone="GMT+8",pattern="yyyy-MM-dd")
    private Date startvaliddate;
    /**
     * 绝对有效期结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone="GMT+8",pattern="yyyy-MM-dd")
    private Date endvaliddate;
    /**
     * 福利数量
     */
    private Integer welfarenum;
    /**
     * 会员等级
     */
    private String memberlevel;
    /**
     * 状态（0：开启 1禁用）
     */
    private Integer status;
    /**
     * 提前天数
     */
    private Integer advancedays;
    /**
     * CreateTime
     */
    private Date createtime;
    /**
     * CreateUser
     */
    private String createuser;
}
