/*
 * 项目名称:platform-plus
 * 类名称:QkjvipNewsCarouselEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-10 13:46:41        liuqianru     初版做成
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
 * @author liuqianru
 * @date 2021-05-10 13:46:41
 */
@Data
@TableName("QKJVIP_NEWS_CAROUSEL")
public class QkjvipNewsCarouselEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 轮播图
     */
    private String carouselimg;
    /**
     * 是否有链接（0：无 1：有）
     */
    private Integer islink;
    /**
     * 跳转链接
     */
    private String linkurl;
    /**
     * 类型
     */
    private Integer carouseltype;
    /**
     * 状态（0：正常 1：禁用）
     */
    private Integer status;
    /**
     * add_user
     */
    private String addUser;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * add_dept
     */
    private String addDept;
}
