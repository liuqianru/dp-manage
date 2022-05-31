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

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 活动台账总计实体
 *
 * @author 刘乾儒
 * @date 2022-05-13 14:49:29
 */
@Data
public class QkjvipMemberActivityAccountEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 计划关怀人数
     */
    @TableField(exist = false)
    private Integer planNum;
    /**
     * 实际关怀人数
     */
    @TableField(exist = false)
    private Integer acceptNum;
    /**
     * 计划关怀总人次
     */
    @TableField(exist = false)
    private Integer planTotalNum;
    /**
     * 实际关怀总人次
     */
    @TableField(exist = false)
    private Integer acceptTotalNum;
}
