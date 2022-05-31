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
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 普通会员导出模板、导入会员实体
 * @author liuqianru
 * @date 2022/5/26 09:21
 */

@Data
public class MemberExcelEntity implements Serializable {

    /*
     * 会员手机
     */
    @Excel(name = "联系方式", orderNum = "1", width = 15)
    private String mobile;
    /**
     * 服务号名称，会员渠道
     */
    @Excel(name = "会员渠道", orderNum = "2", width = 15)
    private String servicename;
    /*
     * 会员昵称
     */
    @Excel(name = "会员名称", orderNum = "3", width = 15)
    private String memberName;
    /**
     * 维护人手机
     */
    @TableField(exist = false)
    @Excel(name = "维护人手机号", orderNum = "4", width = 15)
    private String userMobile;
    /*
     * 会员性别
     */
    @Excel(name = "性别", orderNum = "5", width = 15,replace={"男_1","女_2","未知_3"})
    private Integer sex;
    /*
     * 是否潜在客户(0否1是)
     */
    @Excel(name = "是否潜在客户", orderNum = "6", width = 15,replace={"否_0","是_1"})
    private Integer isCustomers;

    /**
     * 新增或导入时备注
     */
    @Excel(name = "备注", orderNum = "7", width = 15)
    private String remark2;
    /*
     * 年龄
     */
    @Excel(name = "年龄", orderNum = "8", width = 15)
    private Integer age;
    /*
     * 生日
     * 不可修改类型，否则导入的时间为null
     */
    @Excel(name = "生日", exportFormat = "yyyy-MM-dd HH:mm:ss", importFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "9", width = 15)
    private String birthday;
    /*
     * 会员邮件
     */
    @Excel(name = "邮件", orderNum = "10", width = 15)
    private String email;
    /*
     * 加入时间/注册时间
     */
    @Excel(name = "注册时间", exportFormat = "yyyy-MM-dd HH:mm:ss", importFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "11", width = 15)
    private String regTime;
    /*
     * 会员性质
     */
    @Excel(name = "会员性质", orderNum = "12", width = 15, replace={"企业单位_0","事业单位_1","政府机关_2","消费者_3","核心团购客户_4","团购客户_5","核心终端_6","终端_7","酒店_8","核心酒店_9","核心消费者_10","陪同人员_11", "经销商_12"})
    private String memberNature;
    /*
     * 会员来源
     */
    @Excel(name = "会员来源", orderNum = "13", width = 15, replace={"OMS门店_0","线下活动_1","线上活动_2","线上交易_3","线下交易_4","会员推荐_5","旅游景区_6","回厂游_7","其他来源_20"})
    private String memberSource;
    /*
     * 行业类别
     */
    @Excel(name = "行业类别", orderNum = "14", width = 15)
    private String industryType;
    /*
     * 单位性质
     */
    @Excel(name = "单位性质", orderNum = "15", width = 15)
    private String unitProperty;
    /*
     * 公司名称
     */
    @Excel(name = "公司名称", orderNum = "16", width = 15)
    private String companyName;
    /*
     * 职位
     */
    @Excel(name = "职位", orderNum = "17", width = 15)
    private String jobTitle;
    /*
     * 身份证号
     */
    @Excel(name = "身份证", orderNum = "18", width = 15)
    private String idcard;
    /*
     * 标签-省
     */
    @Excel(name = "省", orderNum = "19", width = 15)
    private String tag1;
    /*
     * 标签-市
     */
    @Excel(name = "市", orderNum = "20", width = 15)
    private String tag2;
    /**
     * 会员真实姓名
     */
    private String realName;
    /**
     * 会员渠道号
     */
    private Integer memberchannel;
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
     * 标签数组
     */
    private List<MemberTagsQueryEntity> membertags;

}
