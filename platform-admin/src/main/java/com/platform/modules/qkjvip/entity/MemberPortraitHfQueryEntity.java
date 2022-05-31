/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberLabelEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-26 14:33:22        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.platform.modules.util.Double2Serializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 高频统计检索实体
 *
 * @author liuqianru
 * @date 2021-02-02 10:33:22
 */
@Data
public class MemberPortraitHfQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pageindex;
    private Integer pagesize;
    private Boolean isall;
    private String currentmemberid;
    private String listmemberchannel;
    private Integer daterate;
    private Integer buynum;
    private String listorgno;  // 权限部门
    private Integer isconsultant;  // 0:全部 1：普通 2：政商务
}
