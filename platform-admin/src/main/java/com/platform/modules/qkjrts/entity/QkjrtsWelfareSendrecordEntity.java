/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareSendrecordEntity.java
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
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
@Data
@TableName("QKJRTS_WELFARE_SENDRECORD")
public class QkjrtsWelfareSendrecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * MainId
     */
    private String mainid;
    /**
     * 会员id
     */
    private String memberid;
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
     * 积分
     */
    private Integer integral;
    /**
     * 有效期开始日期
     */
    private Date startvaliddate;
    /**
     * 有效期结束时间
     */
    private Date endvaliddate;
    /**
     * 权益值
     */
    private Double rightvalue;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 场景类型（0：后台手动推 1：生日推 2：新增会员赠送）
     */
    private Integer scenetype;
    /**
     * 发放时间
     */
    private Date sendtime;

    @TableField(exist = false)
    private String membername;
    @TableField(exist = false)
    private String mobile;
    @TableField(exist = false)
    private String jobtitle;
    @TableField(exist = false)
    private String companyname;
    @TableField(exist = false)
    private Integer sex;
    @TableField(exist = false)
    private Integer age;
    @TableField(exist = false)
    private String orguserid;
    @TableField(exist = false)
    private String referrer;
}
