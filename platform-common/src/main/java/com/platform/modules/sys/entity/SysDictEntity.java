/*
 * 项目名称:platform-plus
 * 类名称:SysDictEntity.java
 * 包名称:com.platform.modules.sys.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-01-15 11:42:20        李鹏军     初版做成
 *
 * Copyright (c) 2018-2019 微同软件
 */
package com.platform.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 数据字典实体
 * 表名 sys_dict
 *
 * @author 李鹏军
 * @date 2019-01-15 11:42:20
 */
@Data
@TableName("SYS_DICT")
public class SysDictEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    /**
     * 所属分组ID
     */
    @NotBlank(message = "所属分组不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String groupId;
    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String value;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 状态码
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 标签组类型（1: 输入型标签，2：选项标签）
     */
    /**
     * 父id 级联用
     */
    private String parentid;
    /**
     * 组id 统计用
     */
    private String reportgroupid;

    @TableField(fill = FieldFill.UPDATE)
    private Integer tagType;
    @TableField(fill = FieldFill.UPDATE)
    private Integer optiontype;

    @TableField(exist = false)
    private String code;
    @TableField(exist = false)
    private String value1;
    @TableField(exist = false)
    private String value2;
    @TableField(exist = false)
    private String value3;
    @TableField(exist = false)
    private String name1;
    @TableField(exist = false)
    private String name2;
    @TableField(exist = false)
    private String name3;
}
