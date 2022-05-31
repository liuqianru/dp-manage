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
import java.util.List;

/**
 * 高价值统计返回结果实体
 *
 * @author liuqianru
 * @date 2021-02-02 10:33:22
 */
@Data
public class MemberPortraitAddtrendResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String groupdate;
    private String servicename;
    private Integer memberchannel;
    private Integer count;
}
