/*
 * 项目名称:platform-plus
 * 类名称:QkjvipLotterySettingEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-05 10:59:58        liuqianru     初版做成
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

/**
 * 实体
 *
 * @author liuqianru
 * @date 2021-07-05 10:59:58
 */
@Data
public class QkjvipLotteryPrizeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String prizelevel;  //奖项等级
    private String prizename;   //奖项名称
    private Integer prizenum;   //名额
    private String prizeimg;   //奖品图片
}
