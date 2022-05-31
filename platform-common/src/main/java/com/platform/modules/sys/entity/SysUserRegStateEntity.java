/*
 * 项目名称:platform-plus
 * 类名称:SysUserEntity.java
 * 包名称:com.platform.modules.sys.entity
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    李鹏军      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 *
 * @author 孙珊珊
 */
@Data
@TableName("QKJ_R_USERREG")
public class SysUserRegStateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    private String id;

    private Integer checkState;

    private String checkUser;



}
