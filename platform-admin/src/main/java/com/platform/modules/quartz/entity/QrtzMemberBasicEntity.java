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
package com.platform.modules.quartz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuqianru
 * @date 2020/8/7 14:21
 */

@Data
@TableName("qkjvip_member_basic")
public class QrtzMemberBasicEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String memberId;
    /*
     * 会员手机
     */
    private String cellphone;
    /*
    * 会员真实姓名
     */
    private String realname;
    /**
     * 生日
     */
    private String birthday;
    /*
     * 类型
     */
    private String typename;
    /*
     * 等级
     */
    private String gradename;
    /*
     * 积分
     */
    private Integer integral;
    /*
     * 余额
     */
    private String realtotalprice;
    /*
     * 注册时间(*不可修改数据类型，否则定时任务出错，除非让中酒将日期格式化为"yyyy-MM-dd HH:mm:ss"返回)
     */
    private String createdate;
    /*
     * 会员性别
     */
    private Integer sex;
    /*
     * 所属专卖店
     */
    private String shopname;

    /**
     * isfail
     */
    private Integer isfail;

    /**
     * crm_memberid
     */
    private String crmMemberid;

    /**
     * userid
     */
    private String userid;

    /**
     * province
     */
    private String province;

    /**
     * city
     */
    private String city;

    /**
     * 注册平台类型ID
     */
    private Integer registerplat;

    /**
     * 注册平台类型
     */
    private String registersource;

    @Override
    public boolean equals(Object o) {
        return o instanceof QrtzMemberBasicEntity
                && this.cellphone != null && this.cellphone.equals(((QrtzMemberBasicEntity)o).cellphone)
                && this.shopname != null && this.shopname.equals(((QrtzMemberBasicEntity)o).shopname);
    }

}
