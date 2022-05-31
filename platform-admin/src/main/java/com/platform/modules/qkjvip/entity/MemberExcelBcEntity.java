/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberImportEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-21 15:46:52        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 政商务会员导出模板、导入会员数据实体
 *
 * @author liuqianru
 * @date 2020-09-21 15:46:52
 */
@Data
@TableName("QKJVIP_MEMBER_IMPORT")
public class MemberExcelBcEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
     * 单位名称
     */
    @Excel(name = "单位名称", orderNum = "1", width = 15)
    private String companyName;
    /**
     * 省
     */
    @Excel(name = "收货地址-省", orderNum = "2", width = 15)
    private String province;
    /**
     * 用户所在城市
     */
    @Excel(name = "收货地址-市", orderNum = "3", width = 15)
    private String city;
    /**
     * 地区
     */
    @Excel(name = "收货地址-区县", orderNum = "4", width = 15)
    private String district;
    /*
     * 完成地址
     */
    @Excel(name = "收货地址", orderNum = "5", width = 15)
    private String address;
    /*
     * 客户姓名
     */
    @Excel(name = "客户姓名", orderNum = "6", width = 15)
    private String realName;
    /*
     * 会员手机
     */
    @Excel(name = "客户手机号", orderNum = "7", width = 15)
    private String mobile;
    /*
     * 职务
     */
    @Excel(name = "职务", orderNum = "8", width = 15)
    private String jobTitle;
    /**
     * 身份类型
     */
    @Excel(name = "身份类型", orderNum = "9", width = 15)
    private String identitygroupname;
    private Integer identitygroup;
    /*
     * 身份级别
     */
    @TableField(exist = false)
    @Excel(name = "身份等级", orderNum = "10", width = 15)
    private String identitylevelname;
    private Integer identitylevel;
    /**
     * 区域
     */
    @TableField(exist = false)
    @Excel(name = "区域", orderNum = "11", width = 25)
    private String areaname;
    /*
     * 介绍人姓名
     */
    @TableField(exist = false)
    @Excel(name = "介绍人姓名", orderNum = "12", width = 15)
    private String referrerName;
    /*
     * 介绍人手机
     */
    @TableField(exist = false)
    @Excel(name = "介绍人手机号", orderNum = "13", width = 15)
    private String referrermobile;

    private String referrer;
    private String referrerDept;
    /*
     * 关系维护部门
     */
    @TableField(exist = false)
    @Excel(name = "获客渠道", orderNum = "14", width = 15)
    private String grouporgname;
    private String grouporg;
    /**
     * 维护人姓名
     */
    @TableField(exist = false)
    @Excel(name = "维护人姓名", orderNum = "15", width = 15)
    private String orgUsername;
    /**
     * 维护人手机
     */
    @TableField(exist = false)
    @Excel(name = "维护人手机号", orderNum = "16", width = 15)
    private String userMobile;
    /**
     * 核心店、团购经销商
     */
    @TableField(exist = false)
    @Excel(name = "核心店/团购经销商", orderNum = "17", width = 15)
    private String distributename;
    private String distributeid;
    /*
     * 会员性别
     */
    @Excel(name = "性别", orderNum = "18", width = 15, replace={"男_1","女_2","未知_3"})
    private Integer sex;
    /**
     * 民族
     */
    @Excel(name = "民族", orderNum = "19", width = 15, replace={"汉族_1","壮族_2","回族_3","满族_4","维吾尔族_5","苗族_6","彝族_7","土家族_8","藏族_9","蒙古族_10","侗族_11","布依族_12","瑶族_13","白族_14","朝鲜族_15","哈尼族_16","黎族_17","哈萨克族_18","傣族_19","畲族_20","傈僳族_21","东乡族_22","仡佬族_23","拉祜族_24","佤族_25","水族_26","纳西族_27","羌族_28","土族_29","仫佬族_30","锡伯族_31","柯尔克孜族_32","景颇族_33","达斡尔族_34","撒拉族_35","布朗族_36","毛南族_37","塔吉克族_38","普米族_39","阿昌族_40","怒族_41","鄂温克族_42","京族_43","基诺族_44","德昂族_45","保安族_46","俄罗斯族_47","裕固族_48","乌孜别克族_49","门巴族_50","鄂伦春族_51","独龙族_52","赫哲族_53","高山族_54","珞巴族_55","塔塔尔族_56","其他_57"})
    private String nation;
    /*
     * 生日
     */
    @Excel(name = "生日", exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "20", width = 15)
    private Date birthday;
    /**
     * 年用酒量
     */
    @Excel(name = "年用酒量（公斤）", orderNum = "21", width = 20)
    private String yearwineamt;
    /*
     * 是否喜欢其他名酒(0否1是)
     */
    @Excel(name = "是否喜欢其他名酒", orderNum = "22", width = 20, replace={"否_0","是_1"})
    private Integer islikeother;
    /**
     * 消费能力（价位段）
     */
    @TableField(exist = false)
    @Excel(name = "消费能力（价位段）", orderNum = "23", width = 20)
    private String pricesegmentname;
    private String pricesegment;
    /**
     * 公关团购目的
     */
    @Excel(name = "公关团购目的", orderNum = "24", width = 15)
    private String buypurpose;
    /**
     * 兴趣爱好
     */
    @TableField(exist = false)
    @Excel(name = "兴趣爱好", orderNum = "25", width = 15)
    private String hobbyname;
    private String hobby;
    /**
     * 相关上级行政事业单位
     */
    @Excel(name = "上级单位", orderNum = "26", width = 15)
    private String superiorunit;
    /**
     * 客户方对接人
     */
    @Excel(name = "对接人", orderNum = "27", width = 15)
    private String oppositeperson;

    /**
     * 会员昵称
     */
    private String memberName;
    /**
     * 导入批次号（废弃）
     */
    private String batchno;
    /**
     * 所属办事处
     */
    private String orgNo;
    /**
     * 所属业务员
     */
    private String orgUserid;
    /**
     * 添加人
     */
    private String addUser;
    /**
     * add_dept
     */
    private String addDept;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * 是否是手动导入的会员(1：是）（废弃）
     */
    private Integer offlineflag;
    /**
     * 会员渠道号
     */
    private Integer memberchannel;
    /**
     * 服务号名称，会员渠道
     */
    private String servicename;
    /*
     * 身份类别
     */
    private String membergroup;
    /**
     * 一级区域
     */
    private String areaone;
    /**
     * 二级区域
     */
    private String areatwo;
    /**
     * 三级区域
     */
    private String areathree;

}
