/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-01 11:39:36        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author liuqianru
 * @date 2020-12-01 11:39:36
 */
@Data
@ExcelTarget("qkjvipMemberVisitExportEntity")
public class QkjvipMemberVisitExportEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 会员联系方式
     */
    @Excel(name = "联系方式", mergeVertical = true, orderNum = "0", width = 15)
    @NotBlank(message = "联系方式不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[89]))\\d{8})$", message = "手机号格式错误")
    private String mobile;
    /**
     * 服务号名称，会员渠道
     */
    @Excel(name = "会员渠道", mergeVertical = true, orderNum = "1", width = 15)
    private String servicename;
    /*
     * 会员昵称
     */
    @Excel(name = "会员名称", mergeVertical = true, orderNum = "2", width = 15)
    private String memberName;
    /**
     * 拜访开始时间
     */
    @Excel(name = "拜访开始时间", mergeVertical = true, exportFormat = "yyyy-MM-dd HH:mm:ss", importFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "3", width = 20)
    private Date visitStartDate;
    /**
     * 拜访结束时间
     */
    @Excel(name = "拜访结束时间", mergeVertical = true, exportFormat = "yyyy-MM-dd HH:mm:ss", importFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "4", width = 20)
    private Date visitEndDate;
    /**
     * 拜访类型
     */
    @Excel(name = "拜访方式", mergeVertical = true, orderNum = "5", width = 15, replace={"电话_1","现场_2","微信_3","短信_4","公关赠酒_5", "节日赠礼_6"})
    private String visitType;

    /**
     * 总结
     */
    @Excel(name = "拜访总结", mergeVertical = true, orderNum = "6", width = 15)
    private String content;


    @ExcelCollection(name = "物料", orderNum = "7")
    private List<QkjvipMemberVisitMaterialEntity> materialList;
}
