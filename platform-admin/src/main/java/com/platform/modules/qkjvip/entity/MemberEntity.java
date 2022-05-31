/*
 * 项目名称:platform-plus
 * 类名称:MemberEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/9 14:21            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.emay.util.DateUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import com.platform.modules.quartz.entity.QrtzMemberBasicEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 会员详情实体类
 * @author liuqianru
 * @date 2020/3/9 14:21
 */

@Data
@TableName("qkjvip_member")
public class MemberEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String memberId;
    /**
     * 会员名称
     */
    @NotBlank(message = "会员名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String memberName;
    /*
    * 会员真实姓名
     */
    private String realName;
    /*
     * 会员手机
     */
    @NotBlank(message = "会员手机号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;
    /*
     * 会员邮件
     */
    private String email;
    /*
     * 会员性别
     */
    private Integer sex;
    /*
     * 年龄
     */
    private Integer age;
    /*
     * 生日
     */
    private String birthday;
    /*
     * 会员类型
     */
    private String memberType;
    /*
     * 会员性质
     */
    private String memberNature;
    /*
     * 会员等级
     */
    private String memberLevel;
    /*
     * 会员来源
     */
    private String memberSource;
    /*
     * 行业类别
     */
    private String industryType;
    /*
     * 单位性质
     */
    private String unitProperty;
    /*
     * 公司名称
     */
    private String companyName;
    /*
     * 职位
     */
    private String jobTitle;
    /*
     * 加入时间/注册时间
     */
    private String regTime;
    /*
     * 推荐人部门
     */
    private String referrerDept;
    /*
     * 推荐人
     */
    private String referrer;
    /*
     * 所属办事处
     */
    private String orgNo;
    /*
     * 所属业务人员
     */
    private String orgUserid;
    /*
     * 是否潜在客户(0否1是)
     */
    private Integer isCustomers;
    /*
     * 添加人
     */
    private String addUser;
    /*
     * 添加部门
     */
    private String addDept;
    /*
     * 添加时间
     */
    private Date addTime;
    /*
    * 积分
     */
    private Integer integral;
    /*
    * 余额
     */
    private Double realtotalprice;
    /*
    * 所属店铺
     */
    private String shopname;
    /*
    * 微信id
     */
    private String openid;
    /*
    * 微信头像
     */
    private String headImgUrl;
    /*
    * 会员标签
     */
    @TableField(fill = FieldFill.UPDATE)
    private String memberLabel;
    /*
    * 近一个月购买金额
     */
    private Double amount1;
    /*
     * 近三个月购买金额
     */
    private Double amount3;
    /*
     * 近半年购买金额
     */
    private Double amount6;
    /*
     * 近一年购买金额
     */
    private Double amount12;
    /*
    * 近一个月购买次数
     */
    private Integer buynum1;
    /*
     * 近三个月购买次数
     */
    private Integer buynum3;
    /*
     * 近半年购买次数
     */
    private Integer buynum6;
    /*
     * 近一年购买次数
     */
    private Integer buynum12;
    /*
     * 近一年客单价
     */
    private Double unitprice;

    /*
     * 最后购买日期
     */
    private String lastbuydate;
    /*
    * 身份证号
     */
    private String idcard;
    /*
    * 是否是手动导入的会员(1：是）
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
     * 服务号名称，会员渠道
     */
    private String servicename;
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
     * 新增或导入时备注
     */
    private String remark2;

    /**
     * 觉如积分
     */
    private Integer jueruintegral;
    /**
     * 中酒积分
     */
    private Integer zhongjiuintegral;
    /**
     * 行为积分
     */
    private Integer behaviorintegral;
    /**
     * 奖励积分
     */
     private Integer rewardintegral;
    /**
     * 是否从import表导入标识
     */
    private Integer isimport;
    /**
     * 标识（0：正常 1：删除 2：锁住）
     */
    private Integer statusflag;
    /**
     * 合并过来的会员id
     */
    private String memberidto;
    /**
     * 会员渠道号
     */
    private Integer memberchannel;
    /**
     * 是否同步到青稞荟（废弃）
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
     * 身份类型
     */
    private Integer identitygroup;
    private String identitygroupname;
    /**
     * 会员身份级别
     */
    private Integer identitylevel;
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
     * 成为政商务的时间
     */
    @TableField(exist = false)
    private String addTime2;
    /**
     * 会员id数组
     */
    @TableField(exist = false)
    private List<String> childrenIds;
    /**
     * 圈层(返回结果用)
     */
    @TableField(exist = false)
    private List<MemberLayerEntity> listmemberlayer;
    /**
     * 所属人姓名
     */
    @TableField(exist = false)
    private String orgUsername;
    /**
     * 介绍人姓名
     */
    @TableField(exist = false)
    private String referrerName;
    /**
     * 地区类型（省内-省外）
     */
    @TableField(exist = false)
    private String areaType;

    /**
     * 是否为青稞荟的会员（废弃）
     */
    @TableField(exist = false)
    private Boolean isqkh;

    /**
     * 是否是品牌顾问(0:普通会员 1：品牌顾问)（废弃）
     */
    @TableField(exist = false)
    private Integer membershiptype;
    /**
     * 标签数组
     */
    @TableField(exist = false)
    private List<MemberTagsQueryEntity> membertags;
    /**
     * 来源渠道列表
     */
    @TableField(exist = false)
    private List<MemberChannelEntity> listmemberchannel;
    /**
     * 注册时间
     */
    @TableField(exist = false)
    private String addTimeFormat;
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
     * 当前登录业务员id
     */
    @TableField(exist = false)
    private String currentmemberid;
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

    /**
     * 孙珊添加活动报名用
     */
    @TableField(exist = false)
    private Integer oldsex;
    @TableField(exist = false)
    private String scanphoneno;
    /**
     * 是否是客户端
     */
    @TableField(exist = false)
    private Boolean isclient;
    /**
     * 最后一次参加活动日期
     */
    @TableField(exist = false)
    private String lastactivitydate;
    /**
     * 最后一次参加活动标题
     */
    @TableField(exist = false)
    private String activityTitle;
    /**
     * 活动类型
     */
    @TableField(exist = false)
    private String activeType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }

        MemberEntity other = (MemberEntity) o;
        if (this.memberName == null) {
            if (other.memberName != null) {
                return false;
            }
        } else if (!this.memberName.equals(other.memberName)) {
            return false;
        }

        if (this.realName == null) {
            if (other.realName != null) {
                return false;
            }
        } else if (!this.realName.equals(other.realName)) {
            return false;
        }

        if (this.mobile == null) {
            if (other.mobile != null) {
                return  false;
            }
        } else if (!this.mobile.equals(other.mobile)) {
            return false;
        }

        if (this.memberchannel == null) {
            if (other.memberchannel != null) {
                return  false;
            }
        } else if (!this.memberchannel.equals(other.memberchannel)) {
            return false;
        }

        if (this.email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!this.email.equals(other.email)) {
            return false;
        }

        if (this.sex != other.sex) {
            return false;
        }

        if (this.birthday == null) {
            if (other.birthday != null) {
                return false;
            }
        } else if (!this.birthday.equals(other.birthday)) {
            return false;
        }

        if (this.memberType == null) {
            if (other.memberType != null) {
                return false;
            }
        } else if (!this.memberType.equals(other.memberType)) {
            return false;
        }

        if (this.memberNature == null) {
            if (other.memberNature != null) {
                return false;
            }
        } else if (!this.memberNature.equals(other.memberNature)) {
            return false;
        }

        if (this.memberLevel == null) {
            if (other.memberLevel != null) {
                return false;
            }
        } else if (!this.memberLevel.equals(other.memberLevel)) {
            return false;
        }

        if (this.memberSource == null) {
            if (other.memberSource != null) {
                return false;
            }
        } else if (!this.memberSource.equals(other.memberSource)) {
            return false;
        }

        if (this.industryType == null) {
            if (other.industryType != null) {
                return false;
            }
        } else if (!this.industryType.equals(other.industryType)) {
            return false;
        }

        if (this.unitProperty == null) {
            if (other.unitProperty != null) {
                return false;
            }
        } else if (!this.unitProperty.equals(other.unitProperty)) {
            return false;
        }

        if (this.companyName == null) {
            if (other.companyName != null) {
                return false;
            }
        } else if (!this.companyName.equals(other.companyName)) {
            return false;
        }

        if (this.jobTitle == null) {
            if (other.jobTitle != null) {
                return false;
            }
        } else if (!this.jobTitle.equals(other.jobTitle)) {
            return false;
        }

        if (this.regTime == null) {
            if (other.regTime != null) {
                return false;
            }
        } else if (!this.regTime.equals(other.regTime)) {
            return false;
        }

        if (this.referrerDept == null) {
            if (other.referrerDept != null) {
                return false;
            }
        } else if (!this.referrerDept.equals(other.referrerDept)) {
            return false;
        }

        if (this.referrer == null) {
            if (other.referrer != null) {
                return false;
            }
        } else if (!this.referrer.equals(other.referrer)) {
            return false;
        }

        if (this.orgNo == null) {
            if (other.orgNo != null) {
                return false;
            }
        } else if (!this.orgNo.equals(other.orgNo)) {
            return false;
        }

        if (this.orgUserid == null) {
            if (other.orgUserid != null) {
                return false;
            }
        } else if (!this.orgUserid.equals(other.orgUserid)) {
            return false;
        }

        if (this.remark2 == null) {
            if (other.remark2 != null) {
                return false;
            }
        } else if (!this.remark2.equals(other.remark2)) {
            return false;
        }


        return true;
    }
}
