/*
 * 项目名称:platform-plus
 * 类名称:QkjvipTaglibsEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-26 14:20:07        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

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
 * @date 2020-08-26 14:20:07
 */
@Data
public class MemberTagsQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标签组id
     */
    private String tagGroupId;

    /**
     * 标签组名
     */
    private String tagGroupName;
    /**
     * 被选中的标签的id
     */
    private List<String> tagIdList;
    /**
     * 所有标签的列表
     */
    private List<QkjvipTaglibsEntity> tagList;
    /**
     * 标签类型（1: 输入型标签，2：选项标签，3：只读选项(只有值)，4：只读选择型）
     */
    private Integer tagType;
    /**
     * 标签值（输入型标签才有值）
     */
    private String tagValue;
    /**
     * 选项类型（1：单选 2：多选）
     */
    private Integer optiontype;
}
