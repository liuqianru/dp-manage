/*
 * 项目名称:platform-plus
 * 类名称:QkjvipLotteryWinnersEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-07 16:06:26        liuqianru     初版做成
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
import java.util.Date;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-07-07 16:06:26
 */
@Data
@TableName("QKJVIP_LOTTERY_WINNERS")
public class QkjvipLotteryWinnersEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 奖品
     */
    @Excel(name = "奖品", width = 15, orderNum = "4")
    private String prizename;
    /**
     * 奖项等级
     */
    @Excel(name = "奖项", width = 15, orderNum = "3")
    private String prizelevel;
    /**
     * mainId
     */
    private String mainid;
    /**
     * userId
     */
    private String userid;
    /**
     * createTime
     */
    private Date createtime;

    @TableField(exist = false)
    @Excel(name = "姓名", width = 15, orderNum = "1")
    private String username;
    @TableField(exist = false)
    @Excel(name = "联系方式", width = 15, orderNum = "2")
    private String mobile;
}
