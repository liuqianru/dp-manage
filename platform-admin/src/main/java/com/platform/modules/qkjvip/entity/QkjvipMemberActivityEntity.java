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
 * 实体
 *
 * @author 孙珊珊
 * @date 2020-09-03 09:49:29
 */
@Data
@TableName("QKJVIP_MEMBER_ACTIVITY")
public class QkjvipMemberActivityEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @Excel(name = "主题", orderNum = "1", width = 15, fixedIndex = 1)
    private String title;
    /**
     * 网页内容
     */
    private String content;
    /**
     * 是否签到
     */
    private Integer issign;
    /**
     * id
     */
    @TableId
    private String id;

    /**
     * 隐藏列，必须有，否则groupName会报错
     */
    @Excel(name = "隐藏列", orderNum = "0", isColumnHidden = true, fixedIndex = 0)
    @TableField(exist = false)
    private String memberHidden;

    private String regStarDate;
    private String regEndDate;
    @Excel(name = "开始日期", orderNum = "2", width = 15, fixedIndex = 2)
    private String starDate;
    @Excel(name = "结束日期", orderNum = "3", width = 15, fixedIndex = 3)
    private String endDate;
    private Integer ispri;
    private Integer priPerson;
    private String leading;
    private String remark;
    private Integer isaud;

    private String adduser;
    private String adddept;
    private String address;
    private String bgimg;
    private String issignimg;
    @TableField(exist = false)
    @Excel(name = "类型", orderNum = "4", width = 15, fixedIndex = 4)
    private String atypename;
    private String atype;
    private String secondtype;
    private Integer status;
    private String triplog;
    private String activilog;
    private Date addtime;
    private Integer showtype;//活动详情展示形式Synchronized
    private Integer cardpoints;//活动积分
    private Integer clockpoints; //签到积分
    private Integer isprohibit;//是否禁止未邀约人参加
    private Integer issignup;// 是否显示报名按钮

    private Integer islayer;//是否是圈层

    private Integer channel;

    private Integer checkstatus;

    private String checkfiles;

    private String material;

    private String subject;

    private Integer closestate;

    private Date closetime;

    private String closeimg;

    private String closeremark;

    private Double totalcost;

    private Double percost;

    private Integer sessionnum;

    @TableField(exist = false)
    private Double totalprice;

    private String alevelarea;

    private String blevelarea;

    private String clevelarea;

    private String inchargeuser;
    private String inchargedept;
    @Excel(name = "活动发起人", orderNum = "9", width = 15, fixedIndex = 9)
    private String inchargeusername;

    @TableField(exist = false)
    private List<MemberEntity> members; // 会员json

    private Integer isexcel;
    @TableField(exist = false)
    private String selectmain;
    @TableField(exist = false)
    private String htmlurl;
    @TableField(exist = false)
    private List<QkjvipMemberActivitymbsEntity> mbs;
    @TableField(exist = false)
    private List<QkjvipMemberSignupaddressEntity> addresses;
    @TableField(exist = false)
    private List<QkjvipMemberActivitymaterialEntity> materials;
    @TableField(exist = false)
    private List<QkjvipMemberActivitymaterialEntity> permaterials;
    @TableField(exist = false)
    private List<QkjvipMemberActivityshopEntity> shops;
    @TableField(exist = false)
    private Integer mbsnum;
    @TableField(exist = false)
    private Integer signupnum;
    @TableField(exist = false)
    private Integer smnum;
    @TableField(exist = false)
    private String url;
    @TableField(exist = false)
    private String msgcontent;
    @TableField(exist = false)
    private Integer istake;
    @TableField(exist = false)
    private String addressstr;
    @TableField(exist = false)
    private String realname;
    @TableField(exist = false)
    private Integer activeper;
    @TableField(exist = false)
    private Integer acceptper;
    @TableField(exist = false)
    private Integer signper;
    @TableField(exist = false)
    private Integer isbackqd;
    @TableField(exist = false)
    @Excel(name = "总投入", orderNum = "8", width = 15, fixedIndex = 8)
    private Double totelprice;

    @TableField(exist = false)
    @Excel(name = "一级活动执行区域", orderNum = "5", width = 15, fixedIndex = 5)
    private String alevename;
    @TableField(exist = false)
    @Excel(name = "二级活动执行区域", orderNum = "6", width = 15, fixedIndex = 6)
    private String blevename;
    @TableField(exist = false)
    @Excel(name = "三级活动执行区域", orderNum = "7", width = 15, fixedIndex = 7)
    private String clevename;

    @TableField(exist = false)
    private Boolean isoutsider; // 是否是外部人员，（外部人员查询所有公开活动）
}
