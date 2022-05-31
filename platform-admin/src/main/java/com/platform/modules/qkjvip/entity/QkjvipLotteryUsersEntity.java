/*
 * 项目名称:platform-plus
 * 类名称:QkjvipLotteryUsersEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-05 10:59:58        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-07-05 10:59:58
 */
@Data
@TableName("QKJVIP_LOTTERY_USERS")
public class QkjvipLotteryUsersEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 设置表id
     */
    private String mainid;
    /**
     * 用户名
     */
    @Excel(name = "姓名", orderNum = "1", width = 15)
    private String username;
    /**
     * 联系方式
     */
    @Excel(name = "联系方式", orderNum = "2", width = 15)
    private String mobile;
    /**
     * 部门
     */
    @Excel(name = "部门", orderNum = "3", width = 15)
    private String userdept;
    /**
     * 微信openid
     */
    private String openid;
    /**
     * 微信唯一id
     */
    private String unionid;
    /**
     * 微信头像
     */
    private String headimgurl;

    @TableField(exist = false)
    private String prizelevel;
}
