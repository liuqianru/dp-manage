/*
 * 项目名称:platform-plus
 * 类名称:MemberAddressEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/12 11:45            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * MemberAddressEntity
 *
 * @author liuqianru
 * @date 2020/3/12 11:45
 */
@Data
@TableName("qkjvip_member_address")
public class MemberAddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String uuid;
    /**
     * 会员id
     */
    private String memberId;
    /**
     * 省市区
     */
    private String provinceCityArea;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 邮编
     */
    private String zipCode;
    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String street;
    /**
     * 收货人
     */
    @NotBlank(message = "收货人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String conName;
    /**
     * 收货人手机
     */
    @NotBlank(message = "收货电话不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;
    /**
     * 是否默认地址
     */
    private Integer defaultAddress;
    /**
     * 备注
     */
    private String content;
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
}
