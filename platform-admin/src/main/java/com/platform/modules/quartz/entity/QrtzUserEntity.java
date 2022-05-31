/*
 * 项目名称:platform-plus
 * 类名称:UserEntity.java
 * 包名称:com.platform.modules.quartz.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/25 14:11            liuqianru    初版做成
 *
 */
package com.platform.modules.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * UserEntity
 *
 * @author liuqianru
 * @date 2020/3/25 14:11
 */
@Data
@TableName("hrmresource")
public class QrtzUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
    private String loginid;
    private String lastname;
    private String mobile;
    private String sex;
    private String email;
    private Integer departmentid;
    private String homeaddress;
    private Integer status;
    private Integer accounttype;
}
