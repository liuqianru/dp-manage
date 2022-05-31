/*
 * 项目名称:platform-plus
 * 类名称:AccesstokenEntity.java
 * 包名称:com.platform.modules.accesstoken.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-12-04 16:54:30        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.accesstoken.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2019-12-04 16:54:30
 */
@Data
@TableName("ACCESSTOKEN")
public class AccesstokenEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private String accessToken;
}
