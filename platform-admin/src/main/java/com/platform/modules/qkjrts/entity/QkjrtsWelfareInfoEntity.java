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
public class QkjrtsWelfareInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    private Integer id;
    /**
     * 福利名称
     */
    private String name;
    /**
     * 福利类型
     */
    private Integer welfaretype;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 0 绝对时间 1 相对时间
     */
    private Integer usetimetype;
    /**
     * 相对天数
     */
    private Integer useexpireday;
    /**
     * 有效期开始日期
     */
    private String usestartdate;
    /**
     * 有效期结束时间
     */
    private String useenddate;
}
