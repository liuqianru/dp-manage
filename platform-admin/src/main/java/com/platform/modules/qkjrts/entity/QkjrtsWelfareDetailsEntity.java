/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareDetailsEntity.java
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
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
@Data
@TableName("QKJRTS_WELFARE_DETAILS")
public class QkjrtsWelfareDetailsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * 主表id
     */
    private String mainid;
    /**
     * 福利类型
     */
    private Integer welfaretype;
    /**
     * 有效期类型
     */
    private Integer periodtype;
    /**
     * 有效期天数
     */
    private Integer perioddays;
    /**
     * 有效期开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone="GMT+8",pattern="yyyy-MM-dd")
    private Date startvaliddate;
    /**
     * 有效期结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone="GMT+8",pattern="yyyy-MM-dd")
    private Date endvaliddate;
    /**
     * 福利名称
     */
    private String welfarename;
    /**
     * 福利id
     */
    private Integer welfareid;
    /**
     * 积分
     */
    private Integer integral;
    /**
     * 福利数量
     */
    private Integer welfarenum;
    /**
     * 权益值
     */
    private Double rightvalue;

}
