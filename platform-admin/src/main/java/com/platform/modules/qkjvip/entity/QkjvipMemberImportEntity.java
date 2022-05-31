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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.joda.time.PeriodType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 普通会员导入实体
 *
 * @author liuqianru
 * @date 2020-09-21 15:46:52
 */
@Data
@TableName("QKJVIP_MEMBER_IMPORT")
public class QkjvipMemberImportEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String memberId;
    /**
     * 隐藏列，必须有，否则groupName会报错
     */
    @Excel(name = "隐藏列", orderNum = "0", isColumnHidden = false, fixedIndex = 0)
    @TableField(exist = false)
    private String memberHidden;
    /**
     * 会员手机
     */
    @Excel(name = "联系方式", orderNum = "1", width = 15, groupName = "必填信息", fixedIndex = 1)
    private String mobile;
    /**
     * 服务号名称，会员渠道
     */
    @Excel(name = "会员渠道", orderNum = "2", width = 15, groupName = "必填信息", fixedIndex = 2)
    private String servicename;
    /**
     * 会员名称(昵称)
     */
    @Excel(name = "会员名称", orderNum = "3", width = 15, groupName = "必填信息", fixedIndex = 3)
    private String memberName;
    /**
     * 维护人手机
     */
    @TableField(exist = false)
    @Excel(name = "维护人手机号", orderNum = "4", width = 15, groupName = "必填信息", fixedIndex = 4)
    private String userMobile;
    /**
     * 性别(0男1女）
     */
    @Excel(name = "性别", orderNum = "5", width = 15, groupName = "扩展信息", fixedIndex = 5, replace={"男_1","女_2","未知_3"})
    private Integer sex;
    /**
     * 是否潜在客户(0否1是)
     */
    @Excel(name = "是否潜在客户", orderNum = "6", width = 15,replace={"否_0","是_1"}, groupName = "扩展信息", fixedIndex = 6)
    private Integer isCustomers;

    /**
     * 新增或导入时备注
     */
    @Excel(name = "备注", orderNum = "7", width = 15, groupName = "扩展信息", fixedIndex = 7)
    private String remark2;
    /**
     * 年龄区间
     */
    @Excel(name = "年龄", orderNum = "8", width = 15, groupName = "扩展信息", fixedIndex = 8)
    private Integer age;
    /**
     * 生日
     * 不可修改类型，否则导入的时间为null
     */
    @Excel(name = "生日", importFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "9", width = 15, groupName = "扩展信息", fixedIndex = 9)
    private String birthday;
    /**
     * 会员邮件
     */
    @Excel(name = "邮件", orderNum = "10", width = 15, groupName = "扩展信息", fixedIndex = 10)
    private String email;
    /**
     * 加入时间/注册时间
     * 不可修改类型，否则导入的时间为null
     */
    @Excel(name = "注册时间", importFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "11", width = 15, groupName = "扩展信息", fixedIndex = 11)
    private String regTime;
    /**
     * 会员性质
     */
    @Excel(name = "会员性质", orderNum = "12", width = 15, groupName = "扩展信息", fixedIndex = 12, replace={"企业单位_0","事业单位_1","政府机关_2","消费者_3","核心团购客户_4","团购客户_5","核心终端_6","终端_7","酒店_8","核心酒店_9","核心消费者_10","陪同人员_11", "经销商_12"})
    private String memberNature;
    /**
     * 会员来源
     */
    @Excel(name = "会员来源", orderNum = "13", width = 15, groupName = "扩展信息", fixedIndex = 13, replace={"OMS门店_0","线下活动_1","线上活动_2","线上交易_3","线下交易_4","会员推荐_5","旅游景区_6","回厂游_7", "其他来源_20"})
    private String memberSource;
    /**
     * 行业类别
     */
    @Excel(name = "行业类别", orderNum = "14", width = 15, groupName = "扩展信息", fixedIndex = 14)
    private String industryType;
    /**
     * 单位性质
     */
    @Excel(name = "单位性质", orderNum = "15", width = 15, groupName = "扩展信息", fixedIndex = 15)
    private String unitProperty;
    /**
     * 公司名称
     */
    @Excel(name = "公司名称", orderNum = "16", width = 15, groupName = "扩展信息", fixedIndex = 16)
    private String companyName;
    /**
     * 职位
     */
    @Excel(name = "职位", orderNum = "17", width = 15, groupName = "扩展信息", fixedIndex = 17)
    private String jobTitle;
    /**
     * 身份证
     */
    @Excel(name = "身份证", orderNum = "18", width = 15, groupName = "扩展信息", fixedIndex = 18)
    private String idcard;
    /*
     * 标签-省
     */
    @TableField(exist = false)
    @Excel(name = "省", orderNum = "19", width = 15, groupName = "会员标签", fixedIndex = 19)
    private String tag1;
    /*
     * 标签-市
     */
    @TableField(exist = false)
    @Excel(name = "市", orderNum = "20", width = 15, groupName = "会员标签", fixedIndex = 20)
    private String tag2;
    /**
     * 会员真实姓名
     */
    private String realName;
    /**
     * 推荐人
     */
    private String referrer;
    /**
     * 推荐人部门
     */
    private String referrerDept;
    /**
     * 会员身份级别
     */
    private Integer identitylevel;
    /**
     * 会员类型
     */
    private String memberType;
    /**
     * 所属办事处
     */
    private String orgNo;
    /**
     * 所属业务员
     */
    private String orgUserid;
    /**
     * 积分
     */
    private Integer integral;
    /**
     * 余额
     */
    private BigDecimal realtotalprice;
    /**
     * 微信id
     */
    private String openid;
    /**
     * 微信头像
     */
    private String headImgUrl;
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
     * 会员标签
     */
    private String memberLabel;
    /**
     * 近一个月购买金额
     */
    private Double amount1;
    /**
     * amount3
     */
    private Double amount3;
    /**
     * amount6
     */
    private Double amount6;
    /**
     * amount12
     */
    private Double amount12;
    /**
     * 近一个月购买次数
     */
    private Integer buynum1;
    /**
     * buynum3
     */
    private Integer buynum3;
    /**
     * buynum6
     */
    private Integer buynum6;
    /**
     * buynum12
     */
    private Integer buynum12;
    /**
     * 近一年客单价
     */
    private Double unitprice;
    /**
     * 最后购买日期
     */
    private Date lastbuydate;
    /**
     * 是否是手动导入的会员(1：是）（废弃）
     */
    private Integer offlineflag;
    /**
     * 是否关注
     */
    private Integer isunsubscribe;
    /**
     * 取消关注时间
     */
    private Date unsubscribetime;
    /**
     * 用户的语言，简体中文为zh_CN
     */
    private String language;
    /**
     * 用户所在城市
     */
    private String city;
    /**
     * 用户所在省份
     */
    private String province;
    /**
     * 用户所在国家
     */
    private String country;
    /**
     * 用户关注时间。如果用户曾多次关注，则取最后关注时间
     */
    private Date subscribetime;
    /**
     * UnionId
     */
    private String unionid;
    /**
     * 用户更新时间
     */
    private Date updatetime;
    /**
     * AppId
     */
    private String appid;
    /**
     * 清洗备注
     */
    private String remark;
    /**
     * 清洗失败的标识，失败则为1
     */
    private Integer isfail;
    /**
     * 会员清洗id
     */
    private String crmMemberid;
    /**
     * 会员渠道号
     */
    private Integer memberchannel;
    /**
     * 是否同步到青稞荟
     */
    private Integer toqkh;
    /**
     * 会员等级有效期
     */
    private String validstarttime;
    /**
     * 会员等级有效期
     */
    private String validendtime;
    /**
     * 导入批次号（废弃）
     */
    private String batchno;
    /**
     * 消费者群体
     */
    private String membergroup;
    /**
     * 圈层
     */
    private String memberlayer;
    /**
     * 相关上级行政事业单位
     */
    private String superiorunit;
    /**
     * 客户方对接人
     */
    private String oppositeperson;
    /**
     * 年用酒量
     */
    private String yearwineamt;
    /**
     * 是否喜欢其他名酒(0：否 1：是）
     */
    private Integer islikeother;
    /**
     * 消费能力（价位段）
     */
    private String pricesegment;
    /**
     * 公关团购目的
     */
    private String buypurpose;
    /**
     * 爱好
     */
    private String hobby;
    /**
     * 地区
     */
    private String district;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 销售大区
     */
    private String areacode;
    /**
     * 身份性质分组（报表用）
     */
    private Integer identitygroup;
    private String identitygroupname;
    /**
     * 客户所属
     */
    private String grouporg;
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
    /**
     * 核心店、团购经销商
     */
    private String distributeid;
    /**
     * 民族
     */
    private String nation;
    /**
     * 是否是品牌顾问(0:普通会员 1：品牌顾问)
     */
    @TableField(exist = false)
    private Integer membershiptype;
    /**
     * 标签数组
     */
    @TableField(exist = false)
    private List<MemberTagsQueryEntity> membertags;
    /**
     * 所属部门
     */
    @TableField(exist = false)
    private List<QkjvipMemberDatadepEntity> deptlist;
    /**
     * 所属业务员
     */
    @TableField(exist = false)
    private List<QkjvipMemberOrguserEntity> userlist;
    /**
     * 礼包id
     */
    @TableField(exist = false)
    private Integer welfareid;
    /**
     * 礼包名称
     */
    @TableField(exist = false)
    private String welfarename;
    /**
     * 礼包日期类型（0：绝对 1：相对）
     */
    @TableField(exist = false)
    private Integer periodtype;
    /**
     * 礼包相对天数
     */
    @TableField(exist = false)
    private Integer perioddays;
    /**
     * 礼包绝对开始日期
     */
    @TableField(exist = false)
    private Date startvaliddate;
    /**
     * 礼包绝对结束日期
     */
    @TableField(exist = false)
    private Date endvaliddate;
}
