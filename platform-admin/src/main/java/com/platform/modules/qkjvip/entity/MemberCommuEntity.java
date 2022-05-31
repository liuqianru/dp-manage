/*
 * 项目名称:platform-plus
 * 类名称:MemberCommuEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/13 14:02            liuqianru    初版做成
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
 * MemberCommuEntity
 *
 * @author liuqianru
 * @date 2020/3/13 14:02
 */
@Data
@TableName("qkjvip_member_commu")
public class MemberCommuEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String uuid;
    /**
     * 会员id
     */
    private String memberId;
    /**
     * 联系方式
     */
    @NotBlank(message = "联系方式不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;
    /**
     * 沟通方式
     */
    @NotBlank(message = "联系方式不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String commuType;
    /**
     * 关键词
     */
    private String keywords;
    /**
     * 使用场景
     */
    private String useScene;
    /**
     * 购买意向
     */
    private String buyIntention;
    /**
     * 品类喜好
     */
    private String preference;
    /**
     * 未购买原因
     */
    private String nobuyReason;
    /**
     * 客户意见
     */
    private String opinion;
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
