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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2022-04-14 09:09:27
 */
@Data
public class QkjrtsWelfareMapEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> id;
}
